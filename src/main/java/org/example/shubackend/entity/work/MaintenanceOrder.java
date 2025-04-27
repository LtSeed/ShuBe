package org.example.shubackend.entity.work;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;
import org.example.shubackend.entity.User;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "maintenance_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceOrder extends BaseEntity {
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
    private Instant completedAt;
}