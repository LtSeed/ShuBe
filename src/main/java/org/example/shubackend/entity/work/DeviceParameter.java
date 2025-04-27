package org.example.shubackend.entity.work;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "device_parameters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceParameter extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne
    @JoinColumn(name = "param_type", nullable = false)
    private DataType paramType;

    @Column(name = "param_value", columnDefinition = "json")
    private String paramValue;
}