package org.example.shubackend.entity.work;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "maintenance_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRecord extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private MaintenanceOrder order;

    @Column(columnDefinition = "text")
    private String action;

    private Instant startTime;
    private Instant endTime;
    private java.math.BigDecimal cost;

    @Column(name = "parts_used", columnDefinition = "json")
    private String partsUsed;
}