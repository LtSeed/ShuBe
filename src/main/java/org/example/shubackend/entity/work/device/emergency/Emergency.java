package org.example.shubackend.entity.work.device.emergency;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emergencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emergency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private EmergencyCode code;          // FIRE_ALARM, GAS_LEAK …

    private String name;          // 中文名

    @Embedded
    private EmergencyTriggerCondition triggerCondition; // 事件组合 / 时间窗等
}