package org.example.shubackend.service.work;

import org.example.shubackend.repository.EmergencyPlanRepository;
import org.example.shubackend.entity.work.EmergencyPlan;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class EmergencyPlanCrudService extends GenericCrudService<EmergencyPlan, Integer> {
    public EmergencyPlanCrudService(EmergencyPlanRepository r) {
        super(r);
    }
}
