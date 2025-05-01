/* DeviceEventDTO —— 包含全部字段 */
package org.example.shubackend.dto.device;

public record DeviceEventDTO(
        Integer id,
        String code,            // FireDeviceEventCode name
        Integer priority,
        SimpleDeviceRoleDTO deviceRole,
        String triggerRule,      // JSON 字符串
        EmergencyDTO emergency   // 允许 null
) {
}
