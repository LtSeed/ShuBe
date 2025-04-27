package org.example.shubackend.service.work;

import org.example.shubackend.repository.DeviceRepository;
import org.example.shubackend.entity.work.Device;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class DeviceCrudService extends GenericCrudService<Device, Integer> {
    public DeviceCrudService(DeviceRepository repo) {
        super(repo);
    }
}

