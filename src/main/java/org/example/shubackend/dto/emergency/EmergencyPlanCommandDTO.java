package org.example.shubackend.dto.emergency;

// EmergencyPlanCommandDTO.java
public record EmergencyPlanCommandDTO(
        Integer id,
        Integer emergencyId,   // 仅用 id 关联
        String commands,      // json
        String warnings       // json
) {
}