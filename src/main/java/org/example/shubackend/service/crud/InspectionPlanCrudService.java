package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.inspection.InspectionPlan;
import org.example.shubackend.repository.InspectionPlanRepository;
import org.springframework.stereotype.Service;

@Service
public class InspectionPlanCrudService extends GenericCrudService<InspectionPlan, Integer> {
    public InspectionPlanCrudService(InspectionPlanRepository r) {
        super(r);
    }
}
