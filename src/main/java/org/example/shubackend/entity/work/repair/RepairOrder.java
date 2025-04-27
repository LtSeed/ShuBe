package org.example.shubackend.entity.work.repair;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;
import org.example.shubackend.entity.User;
import org.example.shubackend.entity.work.device.Device;

import java.time.Instant;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "repair_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairOrder extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @Column(columnDefinition = "text")
    private String faultDescription;

    private String status; // 待处理/处理中/已完成/已关闭
    private Instant createdAt;
    private Instant due;
}