package org.example.shubackend.service.work;

import org.example.shubackend.repository.InspectionRecordRepository;
import org.example.shubackend.entity.work.InspectionRecord;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class InspectionRecordCrudService extends GenericCrudService<InspectionRecord, Integer> {
    public InspectionRecordCrudService(InspectionRecordRepository r) {
        super(r);
    }
}
