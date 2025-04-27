package org.example.shubackend.service.device;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.entity.work.device.event.DeviceEvent;
import org.example.shubackend.entity.work.device.event.DeviceEventFired;
import org.example.shubackend.repository.DeviceEventRepository;
import org.example.shubackend.repository.DeviceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TelemetryProcessor {

    private static final long HISTORY_WINDOW_SECONDS = 15 * 60;   // 保留最近 15 分钟
    private final DeviceRepository deviceRepo;
    private final DeviceEventRepository eventRepo;
    private final ApplicationEventPublisher publisher;
    private final ConditionEvaluator evaluator;
    private final Map<Integer, Map<String, Deque<MetricPoint>>> hist = new ConcurrentHashMap<>();
    /**
     * deviceId -> latest metrics JSON map
     */
    private final Map<Integer, Map<String, Object>> snapshots = new ConcurrentHashMap<>();
    /**
     * deviceId -> last seen time
     */
    private final Map<Integer, Instant> lastSeen = new ConcurrentHashMap<>();

    /**
     * 由 MQTT 回调调用
     */
    public void handleTelemetry(Integer deviceId, Map<String, Object> metrics) {
        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("unknown"));
        /* 1) 刷新快照 / 时间序列缓存 */
        updateSnapshot(device, metrics);

        /* 2) 遍历事件，调用新 matches() */
        for (DeviceEvent event : device.getDeviceRole().getEvents()) {

            boolean fired = evaluator.matches(
                    event.getTriggerCondition(),
                    deviceId,
                    metric -> getSeries(deviceId, metric)   // tsSupplier
            );

            if (fired) {
                publisher.publishEvent(
                        new DeviceEventFired(device, event, Instant.now(), metrics));
            }
        }
    }

    public Deque<MetricPoint> getSeries(Integer deviceId, String metric) {
        return hist.getOrDefault(deviceId, Map.of())
                .getOrDefault(metric, new ArrayDeque<>());
    }

    /**
     * Called by DeviceMqttService after it parsed incoming MQTT JSON
     */
    public void updateSnapshot(Device device, Map<String, Object> metrics) {

        Integer deviceId = device.getId();
        // 1) 刷新最新快照和 lastSeen
        snapshots.put(deviceId, metrics);
        Instant now = Instant.now();
        lastSeen.put(deviceId, now);

        // 2) 追加到历史序列
        metrics.forEach((metricKey, val) -> {
            if (!(val instanceof Number num)) return;   // 仅记录数值型指标
            double v = num.doubleValue();

            Deque<MetricPoint> deque = hist
                    .computeIfAbsent(deviceId, id -> new ConcurrentHashMap<>())
                    .computeIfAbsent(metricKey, k -> new ArrayDeque<>());

            deque.addLast(new MetricPoint(v, now));

            // 2-a) 截断窗口外的数据
            Instant threshold = now.minusSeconds(HISTORY_WINDOW_SECONDS);
            while (!deque.isEmpty() && deque.peekFirst().ts().isBefore(threshold)) {
                deque.pollFirst();
            }
        });
    }

    @Transactional
    public void updateSnapshot(Integer deviceId, Map<String, Object> metrics) {
        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("unknown"));
        updateSnapshot(device, metrics);
    }

    /* Helper (optional) – expose latest metrics to other beans */
    public Map<String, Object> latestMetrics(Integer deviceId) {
        return snapshots.get(deviceId);
    }

    public record MetricPoint(double value, Instant ts) {
    }
}



