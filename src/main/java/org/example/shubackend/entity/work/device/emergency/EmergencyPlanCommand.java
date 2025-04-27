package org.example.shubackend.entity.work.device.emergency;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emergency_plan_commands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyPlanCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Emergency emergency;      // 对应预案

    /**
     * 设备指令：“设备id@命令”
     */
    @Column(columnDefinition = "json")
    private String commands;

    /**
     * 角色告警：“roleId@类型@描述”
     */
    @Column(columnDefinition = "json")
    private String warnings;
}
