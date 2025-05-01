// src/main/java/org/example/shubackend/service/dtomapper/device/DeviceRoleMapper.java
package org.example.shubackend.dtomapper.device;

import org.example.shubackend.dto.device.SimpleDeviceRoleDTO;
import org.example.shubackend.entity.work.device.DeviceRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleDeviceRoleMapper {
    SimpleDeviceRoleDTO toDto(DeviceRole entity);
    DeviceRole toEntity(SimpleDeviceRoleDTO dto);
}
