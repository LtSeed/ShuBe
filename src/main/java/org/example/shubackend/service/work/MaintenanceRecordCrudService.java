package org.example.shubackend.service.work;

import org.example.shubackend.repository.MaintenanceRecordRepository;
import org.example.shubackend.entity.work.MaintenanceRecord;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceRecordCrudService extends GenericCrudService<MaintenanceRecord, Integer> {
    public MaintenanceRecordCrudService(MaintenanceRecordRepository r) {
        super(r);
    }
}
