package org.example.shubackend.dtomapper.device;

import org.example.shubackend.dto.device.DeviceRoleDTO;
import org.example.shubackend.entity.work.device.DeviceRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceRoleMapper {
    DeviceRoleDTO toDto(DeviceRole entity);
    DeviceRole toEntity(DeviceRoleDTO dto);
}