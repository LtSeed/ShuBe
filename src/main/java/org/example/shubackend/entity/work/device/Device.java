package org.example.shubackend.entity.work.device;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device extends BaseEntity {
    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @ManyToOne
    @JoinColumn(name = "device_role_id", nullable = false)
    private DeviceRole deviceRole;

    @ManyToOne(optional = false)
    private Location location;          // 仍沿用之前的 Location 实体

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.NORMAL;

    public enum Status {NORMAL, FAULT, OFFLINE}

    public Topic buildTopic() {
        Topic topic = new Topic();

        // 设备的根 Topic，比如 "device/123"
        String rootTopic = "device/" + getId();
        topic.setRoot(rootTopic);

        // 心跳 Topic，比如 "device/123/heartbeat"
        topic.setHeartbeat(rootTopic + "/heartbeat");

        // 数据上传 Topic，比如 "device/123/data"
        topic.setData(rootTopic + "/data");

        return topic;
    }

    @Getter
    @Setter
    public static class Topic {
        private String root;
        private String heartbeat;
        private String data;
    }
}