// src/main/java/org/example/shubackend/service/dtomapper/device/DeviceMapper.java
package org.example.shubackend.dtomapper.device;

import org.example.shubackend.dto.device.DeviceDTO;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.repository.DeviceRoleRepository;
import org.example.shubackend.repository.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = {DeviceRoleMapper.class, LocationMapper.class}
)
public abstract class DeviceMapper {

    @Autowired
    protected DeviceRoleRepository deviceRoleRepo;
    @Autowired
    protected LocationRepository locationRepo;

    /* Entity → DTO */
    @Mapping(target = "status", source = "status")
    public abstract DeviceDTO toDto(Device entity);

    /* DTO → Entity */
    public Device toEntity(DeviceDTO dto) {
        if (dto == null) return null;
        Device e = new Device();
        e.setId(dto.id());
        e.setDeviceName(dto.deviceName());
        // 关联：使用 getReferenceById 拿到 JPA 代理
        e.setDeviceRole(deviceRoleRepo.getReferenceById(dto.deviceRole().id()));
        e.setLocation(locationRepo.getReferenceById(dto.location().id()));
        e.setStatus(Device.Status.valueOf(dto.status()));
        return e;
    }
}
