package org.example.shubackend.entity.work.device.event;

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
public class DeviceEventTriggerCondition {
    /**
     * 监控指标名，如 "temperature"
     */
    private String metric;
    /**
     * 比较符号: GT / LT / EQ / BETWEEN …
     */
    private String operator;
    /**
     * 阈值 JSON，可存单值或数组
     */
    @Column(columnDefinition = "json")
    private String threshold;
}