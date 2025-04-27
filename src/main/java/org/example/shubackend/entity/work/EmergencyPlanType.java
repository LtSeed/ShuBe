package org.example.shubackend.entity.work;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "emergency_plans_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyPlanType extends BaseEntity {
    @Column(nullable = false)
    private String type;
}