package org.example.shubackend.entity.work.inspection;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;
import org.example.shubackend.entity.User;
import org.example.shubackend.entity.work.device.Device;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inspection_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspectionRecord extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private InspectionPlan plan;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne
    @JoinColumn(name = "inspector_id", nullable = false)
    private User inspector;

    @Column(name = "check_items", columnDefinition = "json", nullable = false)
    private String checkItems;

    private String status; // 正常/异常

    @Column(columnDefinition = "text")
    private String issuesFound;

    private Instant inspectionTime;
}