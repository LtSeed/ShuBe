package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.example.shubackend.repository.EmergencyLogRepository;
import org.springframework.stereotype.Service;

@Service
public class EmergencyLogCrudService extends GenericCrudService<EmergencyLog, Long> {
    public EmergencyLogCrudService(EmergencyLogRepository r) {
        super(r);
    }
}

