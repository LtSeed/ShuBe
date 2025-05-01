package org.example.shubackend.dtomapper.inspection;

import org.example.shubackend.dto.SimpleDeviceDTO;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.repository.DeviceRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SimpleDeviceMapper {

    @Autowired
    protected DeviceRepository deviceRepo;

    /**
     * 实体 -> DTO
     */
    @Named("toDeviceDto")
    public SimpleDeviceDTO toDto(Device entity) {
        if (entity == null) {
            return null;
        }
        return new SimpleDeviceDTO(entity.getId(), entity.getDeviceName());
    }

    /**
     * DTO -> 实体，使用 getReferenceById 获取代理
     */
    public Device toEntity(SimpleDeviceDTO dto) {
        if (dto == null) {
            return null;
        }
        return deviceRepo.getReferenceById(dto.id());
    }
}
