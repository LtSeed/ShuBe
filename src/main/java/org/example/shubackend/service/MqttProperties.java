package org.example.shubackend.service;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Getter
@Setter
public class MqttProperties {
    private String brokerUri;
    private String clientId;
    private Topic topic = new Topic();
    private int qos = 1;

    @Getter
    @Setter
    public static class Topic {
        private String root;
        private String heartbeat;
        private String data;
        private String command; // 格式化字符串, 需要 String.format(topic.command, deviceId)
    }

    @Bean
    public MqttClient mqttClient() throws Exception {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setAutomaticReconnect(true);
        options.setCleanStart(true);
        MqttClient client = new MqttClient(brokerUri, clientId, new MemoryPersistence());
        client.connect(options);
        return client;
    }
}