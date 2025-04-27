package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.SparePart;
import org.example.shubackend.repository.SparePartRepository;
import org.springframework.stereotype.Service;

@Service
public class SparePartCrudService extends GenericCrudService<SparePart, Integer> {
    public SparePartCrudService(SparePartRepository r) {
        super(r);
    }
}
