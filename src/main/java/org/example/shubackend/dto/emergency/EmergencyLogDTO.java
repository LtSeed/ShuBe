package org.example.shubackend.dto.emergency;

import java.time.Instant;

public record EmergencyLogDTO(
        Long id,
        Integer deviceId,
        String deviceName,
        EmergencyDTO emergency,
        Instant timestamp
) {
}