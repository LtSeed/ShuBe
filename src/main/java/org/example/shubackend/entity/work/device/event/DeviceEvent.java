package org.example.shubackend.entity.work.device.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shubackend.entity.work.device.DeviceRole;
import org.example.shubackend.entity.work.device.emergency.Emergency;

@Entity
@Table(name = "device_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private FireDeviceEventCode code;          // 如 OVER_TEMP

    private Integer priority;     // 1 = highest

    @ManyToOne(optional = false)
    private DeviceRole deviceRole;

    /* 默认触发条件，可被具体设备覆盖 */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "metric", column = @Column(name = "metric")),
            @AttributeOverride(name = "operator", column = @Column(name = "operator")),
            @AttributeOverride(name = "threshold", column = @Column(name = "threshold_value"))
    })
    private DeviceEventTriggerCondition triggerCondition;

    /* 触发后激活的应急（可空） */
    @ManyToOne
    private Emergency emergency;
}