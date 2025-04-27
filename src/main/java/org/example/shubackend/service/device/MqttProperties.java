package org.example.shubackend.service.device;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Getter
@Setter
public class MqttProperties {
    private String brokerUri;
    private String clientId;
    private int qos = 1;


    @Bean
    public MqttClient mqttClient() throws Exception {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setAutomaticReconnect(true);
        options.setCleanStart(true);
        MqttClient client = new MqttClient(brokerUri, clientId, new MemoryPersistence());
        try {
            client.connect(options);
        } catch (MqttException e) {
            log.error("MqttException: ", e);
        }
        return client;
    }
}