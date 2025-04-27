package org.example.shubackend.entity.work.device.emergency;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyTriggerCondition {
    /**
     * 需满足的事件列表 JSON，比如 ["OVER_TEMP","FLAME_DETECTED"]
     */
    @Column(columnDefinition = "json")
    private String requiredEvents;
    /**
     * 连续触发时间窗（秒）
     */
    private Integer withinSeconds;
}