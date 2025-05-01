// src/main/java/org/example/shubackend/dto/device/DeviceDTO.java
package org.example.shubackend.dto.device;

public record DeviceDTO(
    Integer id,
    String deviceName,
    DeviceRoleDTO deviceRole,
    LocationDTO location,
    String status
) {}
