package org.example.shubackend.service.device;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.event.DeviceEventTriggerCondition;
import org.example.shubackend.entity.work.device.emergency.EmergencyTriggerCondition;
import org.example.shubackend.entity.work.device.event.DeviceEventFired;
import org.springframework.stereotype.*;

import java.time.Instant;
import java.util.*;

/**
 * 解析两类 Embeddable 条件:
 *  • DeviceEventTriggerCondition
 *      metric    = 监控指标 key
 *      operator  = GT | GTE | LT | LTE | EQ | NEQ | BETWEEN
 *      threshold = JSON    (单数 or 数组)
 *
 *  • EmergencyTriggerCondition
 *      requiredEvents = JSON array of event codes
 *      withinSeconds  = 时间窗
 */
@Service
@RequiredArgsConstructor
public class JsonConditionEvaluator implements ConditionEvaluator {

    private final ObjectMapper mapper;

    /* ───────────────── DeviceEvent 触发 ───────────────── */
    @Override
    public boolean matches(DeviceEventTriggerCondition cond,
                           Map<String, Object> metrics) {

        var valueObj = metrics.get(cond.getMetric());
        if (valueObj == null) return false;
        double value = toDouble(valueObj);

        String op = cond.getOperator().toUpperCase(Locale.ENGLISH);
        try {
            switch (op) {
                case "GT":
                    return value > toDouble(singleThreshold(cond));
                case "GTE":
                    return value >= toDouble(singleThreshold(cond));
                case "LT":
                    return value < toDouble(singleThreshold(cond));
                case "LTE":
                    return value <= toDouble(singleThreshold(cond));
                case "EQ":
                    return value == toDouble(singleThreshold(cond));
                case "NEQ":
                    return value != toDouble(singleThreshold(cond));
                case "BETWEEN": {
                    List<Number> range = mapper.readValue(
                            cond.getThreshold(), new TypeReference<>() {
                            });
                    if (range.size() != 2) return false;
                    return value >= range.get(0).doubleValue() &&
                           value <= range.get(1).doubleValue();
                }
                default:
                    return false;
            }
        } catch (Exception e) {
            // malformed JSON
            return false;
        }
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
                                     new TypeReference<List<String>>() {}));
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

    /* ───────────────── Helper ───────────────── */
    private Object singleThreshold(DeviceEventTriggerCondition cond) {
        if (cond.getThreshold() == null) return null;
        try {
            // threshold 既可能是数字也可能是字符串
            return mapper.readValue(cond.getThreshold(), Object.class);
        } catch (Exception e) {
            return null;
        }
    }

    private double toDouble(Object obj) {
        if (obj instanceof Number n)   return n.doubleValue();
        if (obj instanceof String str) return Double.parseDouble(str);
        throw new IllegalArgumentException("Unsupported numeric value: " + obj);
    }
}