package org.example.shubackend.dto.emergency;

// EmergencyDTO.java
public record EmergencyDTO(
        Integer id,
        String code,            // enum name
        String name,
        String triggerRule,     // JSON (requiredEvents), may be null
        Integer withinSeconds
) {
}