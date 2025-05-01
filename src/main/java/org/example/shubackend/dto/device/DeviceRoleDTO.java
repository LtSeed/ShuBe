// src/main/java/org/example/shubackend/dto/device/DeviceRoleDTO.java
package org.example.shubackend.dto.device;

public record DeviceRoleDTO(
        Integer id,
        String roleName,
        String description
) {
}
