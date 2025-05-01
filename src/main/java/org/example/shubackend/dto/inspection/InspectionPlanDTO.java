package org.example.shubackend.dto.inspection;

import org.example.shubackend.dto.SimpleDeviceDTO;
import org.example.shubackend.dto.SimpleUserDTO;

import java.util.Date;

public record InspectionPlanDTO(
        Integer id,
        SimpleDeviceDTO device,
        String frequency,      // 前端仍用字符串
        SimpleUserDTO assignee,
        Date nextDueDate,
        String status
) {
}
