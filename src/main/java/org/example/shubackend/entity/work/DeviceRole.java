package org.example.shubackend.entity.work;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

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
}