package org.example.shubackend.service.work;

import org.example.shubackend.repository.SparePartRepository;
import org.example.shubackend.entity.work.SparePart;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class SparePartCrudService extends GenericCrudService<SparePart, Integer> {
    public SparePartCrudService(SparePartRepository r) {
        super(r);
    }
}
