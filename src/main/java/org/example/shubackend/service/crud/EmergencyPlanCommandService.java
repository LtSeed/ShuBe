package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.emergency.EmergencyPlanCommand;
import org.example.shubackend.repository.EmergencyPlanCommandRepository;
import org.springframework.stereotype.Service;

@Service
public class EmergencyPlanCommandService
        extends GenericCrudService<EmergencyPlanCommand, Integer> {

    public EmergencyPlanCommandService(EmergencyPlanCommandRepository repo) {
        super(repo);
    }
}
