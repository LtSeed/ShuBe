package org.example.shubackend.dto.inspection;

import org.example.shubackend.dto.SimpleDeviceDTO;
import org.example.shubackend.dto.SimpleUserDTO;

import java.time.Instant;

public record InspectionRecordDTO(
        Integer id,
        InspectionPlanDTO plan,
        SimpleDeviceDTO device,
        SimpleUserDTO inspector,
        String checkItems,      // JSON 字符串
        String status,          // 正常 / 异常
        String issuesFound,
        Instant inspectionTime  // ISO-8601
) {
}
