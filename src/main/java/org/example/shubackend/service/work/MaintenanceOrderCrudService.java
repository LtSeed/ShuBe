package org.example.shubackend.service.work;

import org.example.shubackend.repository.MaintenanceOrderRepository;
import org.example.shubackend.entity.work.MaintenanceOrder;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceOrderCrudService extends GenericCrudService<MaintenanceOrder, Integer> {
    public MaintenanceOrderCrudService(MaintenanceOrderRepository r) {
        super(r);
    }
}
