package org.example.shubackend.service.device;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.emergency.EmergencyTriggerCondition;
import org.example.shubackend.entity.work.device.event.DeviceEventFired;
import org.example.shubackend.entity.work.device.event.DeviceEventTriggerCondition;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

/**
 * 解析两类 Embeddable 条件:
 * • DeviceEventTriggerCondition
 * metric    = 监控指标 key
 * operator  = GT | GTE | LT | LTE | EQ | NEQ | BETWEEN
 * threshold = JSON    (单数 or 数组)
 * <p>
 * • EmergencyTriggerCondition
 * requiredEvents = JSON array of event codes
 * withinSeconds  = 时间窗
 */
@Service
@RequiredArgsConstructor
public class JsonConditionEvaluator implements ConditionEvaluator {

    private final ObjectMapper mapper;

    /* -------- 事件触发（复杂表达式） -------- */
    @Override
    public boolean matches(DeviceEventTriggerCondition cond,
                           Integer deviceId,
                           Function<String, Deque<TelemetryProcessor.MetricPoint>> tsSupplier) {
        try {
            JsonNode root = mapper.readTree(cond.getRule());
            String expr = root.get("expr").asText();
            JsonNode preds = root.get("pred");

            return evalExpr(expr, name -> checkPredicate(
                    preds.get(name), deviceId, tsSupplier));
        } catch (Exception e) {
            return false;
        }
    }

    /* 递归解析 (AND OR NOT) —— 简化版，仅示意 */
    private boolean evalExpr(String expr, Function<String, Boolean> atom) {
        expr = expr.trim();
        // 去括号
        if (expr.startsWith("(") && expr.endsWith(")"))
            return evalExpr(expr.substring(1, expr.length() - 1), atom);
        // OR
        int idx = expr.lastIndexOf(" OR ");
        if (idx > 0) return evalExpr(expr.substring(0, idx), atom)
                || evalExpr(expr.substring(idx + 4), atom);
        // AND
        idx = expr.lastIndexOf(" AND ");
        if (idx > 0) return evalExpr(expr.substring(0, idx), atom)
                && evalExpr(expr.substring(idx + 5), atom);
        // NOT
        if (expr.startsWith("NOT ")) return !evalExpr(expr.substring(4), atom);
        // atom
        return atom.apply(expr);
    }

    /**
     * 检查单个谓词（含持续时长 & after 依赖）
     */
    private boolean checkPredicate(JsonNode node,
                                   Integer deviceId,
                                   Function<String, Deque<TelemetryProcessor.MetricPoint>> tsSupplier) {

        String metric = node.get("metric").asText();
        String op = node.get("op").asText();
        double value = node.get("value").asDouble();
        long seconds = node.has("seconds") ? node.get("seconds").asLong() : 0;
        String after = node.has("after") ? node.get("after").asText() : null;

        Deque<TelemetryProcessor.MetricPoint> series = tsSupplier.apply(metric);

        // 1) 若有 after 依赖，找出 t0 = 第一次满足 after 的时间
        Instant t0 = Instant.EPOCH;
        if (after != null) {
            for (TelemetryProcessor.MetricPoint p : series) {
                if (evalSimple(node.path("../" + after), p.value())) { // pseudo
                    t0 = p.ts();
                    break;
                }
            }
        }

        Instant winStart = seconds > 0 ? Instant.now().minusSeconds(seconds) : Instant.EPOCH;
        if (t0.isAfter(winStart)) winStart = t0;

        // 2) 在 [winStart, now] 期间所有点都满足？
        Instant finalWinStart = winStart;
        return series.stream()
                .filter(p -> !p.ts().isBefore(finalWinStart))
                .allMatch(p -> evalSimple(node, p.value()));
    }

    private boolean evalSimple(JsonNode node, double v) {
        String op = node.get("op").asText();
        double th = node.get("value").asDouble();
        return switch (op) {
            case "LT" -> v < th;
            case "LTE" -> v <= th;
            case "GT" -> v > th;
            case "GTE" -> v >= th;
            case "EQ" -> v == th;
            case "NEQ" -> v != th;
            default -> false;
        };
    }


    /* ───────────────── Emergency 触发 ───────────────── */
    @Override
    public boolean emergencyMatches(EmergencyTriggerCondition cond,
                                    Collection<DeviceEventFired> recentEvents,
                                    Instant now) {

        if (cond == null || cond.getRequiredEvents() == null) return false;

        try {
            Set<String> required = new HashSet<>(
                    mapper.readValue(cond.getRequiredEvents(),
                            new TypeReference<List<String>>() {
                            }));
            long window = Optional.ofNullable(cond.getWithinSeconds()).orElse(0);

            Instant windowStart = window > 0 ? now.minusSeconds(window) : Instant.EPOCH;

            // 收集时间窗内发生的事件 code
            Set<String> occurred = new HashSet<>();
            for (DeviceEventFired fired : recentEvents) {
                if (!fired.timestamp().isBefore(windowStart)) {
                    occurred.add(fired.meta().getCode().name());
                }
            }
            return occurred.containsAll(required);

        } catch (Exception e) {
            return false;
        }
    }

}