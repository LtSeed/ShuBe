package org.example.shubackend.service.work;

import org.example.shubackend.repository.EmergencyPlanTypeRepository;
import org.example.shubackend.entity.work.EmergencyPlanType;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class EmergencyPlanTypeCrudService extends GenericCrudService<EmergencyPlanType, Integer> {
    public EmergencyPlanTypeCrudService(EmergencyPlanTypeRepository r) {
        super(r);
    }
}
