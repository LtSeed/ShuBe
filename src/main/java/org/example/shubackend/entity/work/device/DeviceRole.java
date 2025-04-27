package org.example.shubackend.entity.work.device;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;
import org.example.shubackend.entity.work.device.event.DeviceEvent;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "device_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRole extends BaseEntity {
    @Column(name = "role_name", nullable = false)
    private String roleName;
    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "deviceRole", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeviceEvent> events;    // 该角色可触发哪些事件
}