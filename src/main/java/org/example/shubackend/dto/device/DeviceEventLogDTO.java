/* DeviceEventLogDTO —— 全部字段 */
package org.example.shubackend.dto.device;

import org.example.shubackend.dto.SimpleDeviceDTO;

import java.time.Instant;

public record DeviceEventLogDTO(
        Long id,
        SimpleDeviceDTO device,
        DeviceEventDTO event,     // 这里只需要 id、code 亦可
        Instant timestamp
) {
}