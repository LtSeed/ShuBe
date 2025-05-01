package org.example.shubackend.dtomapper.emergency;

import org.example.shubackend.dto.emergency.EmergencyPlanCommandDTO;
import org.example.shubackend.entity.work.device.emergency.EmergencyPlanCommand;
import org.example.shubackend.repository.EmergencyRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EmergencyPlanCommandMapper {

    @Autowired
    protected EmergencyRepository emergencyRepo;

    public EmergencyPlanCommandDTO toDto(EmergencyPlanCommand e) {
        if (e == null) return null;
        return new EmergencyPlanCommandDTO(
                e.getId(), e.getEmergency().getId(), e.getCommands(), e.getWarnings()
        );
    }

    public EmergencyPlanCommand toEntity(EmergencyPlanCommandDTO dto) {
        if (dto == null) return null;
        EmergencyPlanCommand e = new EmergencyPlanCommand();
        e.setId(dto.id());
        e.setEmergency(emergencyRepo.getReferenceById(dto.emergencyId()));
        e.setCommands(dto.commands());
        e.setWarnings(dto.warnings());
        return e;
    }
}
