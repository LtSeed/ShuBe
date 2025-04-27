package org.example.shubackend.service.device;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.example.shubackend.repository.DeviceRepository;
import org.example.shubackend.service.MqttProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class DeviceMqttService implements MqttCallback {

    private final MqttClient client;
    private final MqttProperties props;
    private final DeviceRepository deviceRepo;

    // 记录最近一次心跳时间
    private final Map<Integer, Instant> heartbeatMap = new ConcurrentHashMap<>();

    @Value("${heartbeat.timeout-ms:1000}")
    private long heartbeatTimeoutMs;

    @PostConstruct
    public void init() throws Exception {
        client.setCallback(this);
        client.subscribe(props.getTopic().getHeartbeat(), props.getQos());
        client.subscribe(props.getTopic().getData(), props.getQos());
        log.info("✅ MQTT subscribed to topics: {} , {}", props.getTopic().getHeartbeat(), props.getTopic().getData());
    }

    @PreDestroy
    public void shutdown() throws Exception {
        if (client.isConnected()) client.disconnect();
    }

    // 250ms 轮询：检查心跳超时并标记离线
    @Scheduled(fixedRate = 250)
    public void checkHeartbeat() {
        Instant now = Instant.now();
        heartbeatMap.forEach((deviceId, lastSeen) -> {
            if (now.toEpochMilli() - lastSeen.toEpochMilli() > heartbeatTimeoutMs) {
                deviceRepo.findById(deviceId).ifPresent(d -> {
                    if (!"offline".equalsIgnoreCase(d.getStatus())) {
                        d.setStatus("offline");
                        deviceRepo.save(d);
                        log.warn("🚨 Device {} offline (last heartbeat {})", deviceId, lastSeen);
                    }
                });
            }
        });
    }

    // 250ms 轮询：向设备发布心跳请求 (可选)
    @Scheduled(fixedRate = 250, initialDelay = 50)
    public void publishHeartbeatRequest() {
        heartbeatMap.keySet().forEach(deviceId -> {
            String topic = String.format(props.getTopic().getCommand(), deviceId);
            try {
                client.publish(topic, new MqttMessage("ping".getBytes(StandardCharsets.UTF_8)));
            } catch (MqttException e) {
                log.error("Publish heartbeat to {} failed", topic, e);
            }
        });
    }

    // ===== MqttCallback Impl =====

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {

    }

    @Override
    public void mqttErrorOccurred(MqttException exception) {
        log.error("MQTT error", exception);
    }

    @Override
    public void deliveryComplete(IMqttToken token) { /* no‑op */ }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("MQTT connected {}", serverURI);
    }

    @Override
    public void authPacketArrived(int i, org.eclipse.paho.mqttv5.common.packet.MqttProperties mqttProperties) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.debug("📥 MQTT [{}] {}", topic, message);
        if (topic.matches(props.getTopic().getHeartbeat().replace("+", "\\d+"))) {
            int deviceId = extractDeviceId(topic);
            heartbeatMap.put(deviceId, Instant.now());
            deviceRepo.findById(deviceId).ifPresent(d -> {
                if (!"normal".equalsIgnoreCase(d.getStatus())) {
                    d.setStatus("normal");
                    deviceRepo.save(d);
                }
            });
        } else if (topic.matches(props.getTopic().getData().replace("+", "\\d+"))) {
            int deviceId = extractDeviceId(topic);
            // TODO: 解析 JSON 载荷并写入 device_parameters / inspection_records 等表
        }
    }

    private int extractDeviceId(String topic) {
        // fpfm/device/{id}/xxx  → 取 {id}
        String[] seg = topic.split("/");
        return Integer.parseInt(seg[2]);
    }
}
