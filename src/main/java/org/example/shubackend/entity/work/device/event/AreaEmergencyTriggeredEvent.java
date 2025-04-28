package org.example.shubackend.entity.work.device.event;

import org.example.shubackend.entity.work.device.emergency.EmergencyCode;

import java.time.Instant;

public record AreaEmergencyTriggeredEvent(
        String areaLocation,
        EmergencyCode code,
        Instant timestamp
) {
}