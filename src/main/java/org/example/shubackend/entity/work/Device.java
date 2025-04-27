package org.example.shubackend.entity.work;

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

    @ManyToOne
    @JoinColumn(name = "facility_type_id", nullable = false)
    private FacilityType facilityType;

    // 若将 location_id 改整型外键，可改 ManyToOne；此处保持字符串
    private String locationId;

    @Column(nullable = false)
    private String status; // normal / fault / offline
}