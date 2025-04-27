package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.inspection.InspectionRecord;
import org.example.shubackend.repository.InspectionRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class InspectionRecordCrudService extends GenericCrudService<InspectionRecord, Integer> {
    public InspectionRecordCrudService(InspectionRecordRepository r) {
        super(r);
    }
}
