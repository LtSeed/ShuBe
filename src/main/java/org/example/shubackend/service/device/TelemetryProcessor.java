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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TelemetryProcessor {

    private final DeviceRepository deviceRepo;
    private final DeviceEventRepository eventRepo;
    private final ApplicationEventPublisher publisher;
    private final ConditionEvaluator evaluator;
    /**
     * 由 MQTT 回调调用
     */
    public void handleTelemetry(Integer deviceId, Map<String, Object> metrics) {
        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("unknown"));
        // 1) 刷新状态缓存
        updateSnapshot(device, metrics);

        // 2) 遍历角色事件判断
        for (DeviceEvent event : device.getDeviceRole().getEvents()) {
            if (evaluator.matches(event.getTriggerCondition(), metrics)) {
                publisher.publishEvent(new DeviceEventFired(device, event, Instant.now(), metrics));
            }
        }
    }

    /** deviceId -> latest metrics JSON map */
    private final Map<Integer, Map<String,Object>> snapshots = new ConcurrentHashMap<>();

    /** deviceId -> last seen time */
    private final Map<Integer, Instant> lastSeen = new ConcurrentHashMap<>();

    /** Called by DeviceMqttService after it parsed incoming MQTT JSON */
    public void updateSnapshot(Device device, Map<String,Object> metrics) {
        snapshots.put(device.getId(), metrics);
        lastSeen.put(device.getId(), Instant.now());
    }

    @Transactional
    public void updateSnapshot(Integer deviceId, Map<String,Object> metrics) {
        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("unknown"));
        updateSnapshot(device, metrics);
    }

    /* Helper (optional) – expose latest metrics to other beans */
    public Map<String, Object> latestMetrics(Integer deviceId) {
        return snapshots.get(deviceId);
    }
}
