package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.repair.RepairRecord;
import org.example.shubackend.repository.RepairRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class RepairRecordCrudService extends GenericCrudService<RepairRecord, Integer> {
    public RepairRecordCrudService(RepairRecordRepository r) {
        super(r);
    }
}
