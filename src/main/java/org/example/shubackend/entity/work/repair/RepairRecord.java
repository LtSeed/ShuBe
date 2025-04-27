package org.example.shubackend.entity.work.repair;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "repair_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairRecord extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private RepairOrder order;

    @Column(columnDefinition = "text")
    private String action;

    private Instant startTime;
    private Instant endTime;
    private java.math.BigDecimal cost;

    @Column(name = "parts_used", columnDefinition = "json")
    private String partsUsed;
}