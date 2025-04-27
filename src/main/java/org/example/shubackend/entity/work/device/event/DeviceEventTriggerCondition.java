package org.example.shubackend.entity.work.device.event;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 复杂触发规则（可嵌套 AND / OR / NOT，支持持续时长）
 * <p>
 * 保存为 JSON：
 * {
 * "expr" : "(low30s AND unrecover10m)",
 * "pred" : {
 * "low30s"       : { "metric":"pressure", "op":"LT", "value":0.35, "seconds":30 },
 * "unrecover10m" : { "metric":"pressure", "op":"LT", "value":0.4 , "seconds":600 , "after":"low30s"}
 * }
 * }
 * <p>
 * 说明:
 * • expr  —— 字符串表达式，可用括号、AND、OR、NOT（大小写均可）
 * • pred.* —— 每个原子谓词:
 * metric   监测指标
 * op       GT / GTE / LT / LTE / EQ / NEQ / BETWEEN
 * value    单值或 [min,max]
 * seconds  持续时长；若省略则瞬时判断
 * after    依赖另一个谓词首次满足后才计时
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class DeviceEventTriggerCondition {

    @Column(columnDefinition = "json")
    private String rule;     // 上面 JSON 字符串
}