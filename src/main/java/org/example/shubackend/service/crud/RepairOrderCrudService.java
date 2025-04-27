package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.repair.RepairOrder;
import org.example.shubackend.repository.RepairOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class RepairOrderCrudService extends GenericCrudService<RepairOrder, Integer> {
    public RepairOrderCrudService(RepairOrderRepository r) {
        super(r);
    }
}
