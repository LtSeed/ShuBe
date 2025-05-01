package org.example.shubackend.dtomapper.device;

import org.example.shubackend.dto.SimpleDeviceDTO;
import org.example.shubackend.dto.device.DeviceEventLogDTO;
import org.example.shubackend.entity.work.device.event.DeviceEventLog;
import org.example.shubackend.repository.DeviceEventRepository;
import org.example.shubackend.repository.DeviceRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = DeviceEventMapper.class)
public abstract class DeviceEventLogMapper {

    @Autowired
    protected DeviceRepository deviceRepo;
    @Autowired
    protected DeviceEventRepository eventRepo;
    @Autowired
    protected DeviceEventMapper eventMapper;

    /* Entity -> DTO */
    public DeviceEventLogDTO toDto(DeviceEventLog e) {
        if (e == null) return null;
        return new DeviceEventLogDTO(
                e.getId(),
                new SimpleDeviceDTO(e.getDevice().getId(), e.getDevice().getDeviceName()),
                eventMapper.toDto(e.getEvent()),
                e.getTimestamp()
        );
    }

    /* DTO -> Entity */
    public DeviceEventLog toEntity(DeviceEventLogDTO dto) {
        if (dto == null) return null;
        DeviceEventLog e = new DeviceEventLog();
        e.setId(dto.id());
        e.setDevice(deviceRepo.getReferenceById(dto.device().id()));
        e.setEvent(eventRepo.getReferenceById(dto.event().id()));
        e.setTimestamp(dto.timestamp());
        return e;
    }
}
