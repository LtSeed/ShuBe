package org.example.shubackend.service.work;

import org.example.shubackend.repository.FacilityTypeRepository;
import org.example.shubackend.entity.work.FacilityType;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class FacilityTypeCrudService extends GenericCrudService<FacilityType, Integer> {
    public FacilityTypeCrudService(FacilityTypeRepository r) {
        super(r);
    }
}
