package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.emergency.AreaEmergencyLog;
import org.example.shubackend.repository.AreaEmergencyLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AreaEmergencyLogCrudService
        extends GenericCrudService<AreaEmergencyLog, Integer> {

    public AreaEmergencyLogCrudService(AreaEmergencyLogRepository repo) {
        super(repo);
    }
}
