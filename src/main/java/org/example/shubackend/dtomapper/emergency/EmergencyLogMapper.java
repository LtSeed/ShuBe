package org.example.shubackend.dtomapper.emergency;

import org.example.shubackend.dto.emergency.EmergencyLogDTO;
import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.example.shubackend.repository.DeviceRepository;
import org.example.shubackend.repository.EmergencyRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = EmergencyMapper.class)
public abstract class EmergencyLogMapper {

    @Autowired
    protected DeviceRepository deviceRepo;
    @Autowired
    protected EmergencyRepository emergencyRepo;
    @Autowired
    protected EmergencyMapper emergencyMapper;

    public EmergencyLogDTO toDto(EmergencyLog l) {
        if (l == null) return null;
        return new EmergencyLogDTO(
                l.getId(),
                l.getDevice().getId(),
                l.getDevice().getDeviceName(),
                emergencyMapper.toDto(l.getEmergency()),
                l.getTimestamp()
        );
    }

    public EmergencyLog toEntity(EmergencyLogDTO dto) {
        if (dto == null) return null;
        EmergencyLog l = new EmergencyLog();
        l.setId(dto.id());
        l.setDevice(deviceRepo.getReferenceById(dto.deviceId()));
        l.setEmergency(emergencyRepo.getReferenceById(dto.emergency().id()));
        l.setTimestamp(dto.timestamp());
        return l;
    }
}
