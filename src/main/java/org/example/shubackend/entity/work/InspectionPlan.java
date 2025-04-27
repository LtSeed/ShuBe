package org.example.shubackend.entity.work;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;
import org.example.shubackend.entity.User;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inspection_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspectionPlan extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    private String frequency; // enum in DB

    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    private java.sql.Date nextDueDate;
    private String status; // 未开始/进行中/已完成
}