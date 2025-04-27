package org.example.shubackend.service.device;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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
    private final ObjectMapper objectMapper;
    private final TelemetryProcessor telemetryProcessor;

    @Value("${heartbeat.timeout-ms:1000}")
    private long heartbeatTimeoutMs;

    @PostConstruct
    public void init() throws Exception {
        client.setCallback(this);
        List<Device> devices = deviceRepo.findAll();
        for (Device device : devices) {
            Device.Topic topic = device.buildTopic();
            try {
                client.subscribe(topic.getHeartbeat(), props.getQos());
                client.subscribe(topic.getData(), props.getQos());
                log.info("✅ MQTT subscribed to topics: {} , {}", topic.getHeartbeat(), topic.getData());
            } catch (MqttException e) {
                log.error("MQTT subscribe error :{}", e.getMessage());
            }
        }
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
                    if (!d.getStatus().equals(Device.Status.OFFLINE)) {
                        d.setStatus(Device.Status.OFFLINE);
                        deviceRepo.save(d);
                        log.warn("🚨 Device {} offline (last heartbeat {})", deviceId, lastSeen);
                    }
                });
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

        List<Device> devices = deviceRepo.findAll();
        for (Device device : devices) {
            Device.Topic topic1 = device.buildTopic();
            // ---------- 1)  heartbeat ----------
            if (topic.matches(topic1.getHeartbeat().replace("+", "\\\\d+"))) {
                int deviceId = extractDeviceId(topic);

                heartbeatMap.put(deviceId, Instant.now());        // refresh heartbeat

                deviceRepo.findById(deviceId).ifPresent(d -> {
                    if (!d.getStatus().equals(Device.Status.NORMAL)) {
                        d.setStatus(Device.Status.NORMAL);
                        deviceRepo.save(d);
                    }
                });
                return;   // nothing else to do
            }

            // ---------- 2)  telemetry DATA ----------
            if (topic.matches(topic1.getData().replace("+", "\\\\d+"))) {
                int deviceId = extractDeviceId(topic);
                try {
                    // JSON payload -> Map<String,Object>
                    Map<String, Object> metrics = objectMapper
                            .readValue(message.getPayload(), new TypeReference<>() {
                            });

                    // persist snapshot & evaluate events
                    telemetryProcessor.handleTelemetry(deviceId, metrics);

                } catch (Exception e) {
                    log.error("❌ Unable to parse telemetry JSON from device {}", deviceId, e);
                }
            }
        }


    }

    private int extractDeviceId(String topic) {
        // fpfm/device/{id}/xxx  → 取 {id}
        String[] seg = topic.split("/");
        return Integer.parseInt(seg[2]);
    }
}
