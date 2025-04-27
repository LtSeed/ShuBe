package org.example.shubackend.entity.work;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emergency_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyPlan {
    @Id
    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @ManyToOne
    @JoinColumn(name = "plan_type", nullable = false)
    private EmergencyPlanType planType;

    @Column(columnDefinition = "text")
    private String description;
}