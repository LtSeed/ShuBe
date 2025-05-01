// src/main/java/org/example/shubackend/dto/repair/RepairRecordDTO.java
package org.example.shubackend.dto.repair;

import java.math.BigDecimal;
import java.time.Instant;

public record RepairRecordDTO(
        Integer id,
        RepairOrderDTO order,
        String action,
        Instant startTime,
        Instant endTime,
        BigDecimal cost,
        String partsUsed
) {
}
