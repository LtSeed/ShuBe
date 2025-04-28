package org.example.shubackend.entity.work.device.emergency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "area_emergency_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaEmergencyLog extends BaseEntity {

    /**
     * 区域标识，如 “2@3x5_AREA” 或自定义分区名
     */
    private String areaLocation;

    @Enumerated(EnumType.STRING)
    private EmergencyCode code;

    private Instant timestamp;
}