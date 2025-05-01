// src/main/java/org/example/shubackend/dto/repair/RepairOrderDTO.java
package org.example.shubackend.dto.repair;


import org.example.shubackend.dto.SimpleDeviceDTO;
import org.example.shubackend.dto.SimpleUserDTO;

import java.time.Instant;

public record RepairOrderDTO(
        Integer id,
        SimpleDeviceDTO device,
        SimpleUserDTO reporter,
        SimpleUserDTO assignee,
        String faultDescription,
        String status,
        Instant createdAt,
        Instant due
) {
}
