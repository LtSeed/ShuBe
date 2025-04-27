package org.example.shubackend.service.work;

import org.example.shubackend.repository.InspectionPlanRepository;
import org.example.shubackend.entity.work.InspectionPlan;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class InspectionPlanCrudService extends GenericCrudService<InspectionPlan, Integer> {
    public InspectionPlanCrudService(InspectionPlanRepository r) {
        super(r);
    }
}
