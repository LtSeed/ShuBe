package org.example.shubackend.dtomapper.emergency;

import org.example.shubackend.dto.emergency.EmergencyDTO;
import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyCode;
import org.example.shubackend.entity.work.device.emergency.EmergencyTriggerCondition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class EmergencyMapper {

    public EmergencyDTO toDto(Emergency e) {
        if (e == null) return null;
        return new EmergencyDTO(
                e.getId(),
                e.getCode().name(),
                e.getName(),
                e.getTriggerCondition() == null ? null : e.getTriggerCondition().getRequiredEvents(),
                e.getTriggerCondition() == null ? null : e.getTriggerCondition().getWithinSeconds()
        );
    }

    public Emergency toEntity(EmergencyDTO dto) {
        if (dto == null) return null;
        Emergency e = new Emergency();
        e.setId(dto.id());
        e.setCode(EmergencyCode.valueOf(dto.code()));
        e.setName(dto.name());
        if (dto.triggerRule() != null || dto.withinSeconds() != null) {
            e.setTriggerCondition(new EmergencyTriggerCondition(dto.triggerRule(), dto.withinSeconds()));
        }
        return e;
    }
}
