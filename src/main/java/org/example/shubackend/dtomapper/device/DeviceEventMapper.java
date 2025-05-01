package org.example.shubackend.dtomapper.device;

import org.example.shubackend.dto.device.DeviceEventDTO;
import org.example.shubackend.dto.device.SimpleDeviceRoleDTO;
import org.example.shubackend.dto.device.EmergencyDTO;
import org.example.shubackend.entity.work.device.event.DeviceEvent;
import org.example.shubackend.entity.work.device.event.FireDeviceEventCode;
import org.example.shubackend.repository.DeviceRoleRepository;
import org.example.shubackend.repository.EmergencyRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DeviceEventMapper {

    @Autowired
    protected DeviceRoleRepository deviceRoleRepo;
    @Autowired
    protected EmergencyRepository emergencyRepo;

    /* ========== Entity -> DTO ========== */
    public DeviceEventDTO toDto(DeviceEvent e) {
        if (e == null) return null;
        return new DeviceEventDTO(
                e.getId(),
                e.getCode().name(),
                e.getPriority(),
                e.getDeviceRole() == null ? null :
                        new SimpleDeviceRoleDTO(e.getDeviceRole().getId(), e.getDeviceRole().getRoleName()),
                e.getTriggerCondition() == null ? null : e.getTriggerCondition().getRule(),
                e.getEmergency() == null ? null :
                        new EmergencyDTO(e.getEmergency().getId(), e.getEmergency().getName())
        );
    }

    /* ========== DTO -> Entity ========== */
    public DeviceEvent toEntity(DeviceEventDTO dto) {
        if (dto == null) return null;
        DeviceEvent e = new DeviceEvent();
        e.setId(dto.id());
        e.setCode(FireDeviceEventCode.valueOf(dto.code()));
        e.setPriority(dto.priority());
        e.setDeviceRole(deviceRoleRepo.getReferenceById(dto.deviceRole().id()));
        if (dto.triggerRule() != null) {
            var cond = new org.example.shubackend.entity.work.device.event.DeviceEventTriggerCondition();
            cond.setRule(dto.triggerRule());
            e.setTriggerCondition(cond);
        }
        e.setEmergency(dto.emergency() == null ? null :
                emergencyRepo.getReferenceById(dto.emergency().id()));
        return e;
    }
}
