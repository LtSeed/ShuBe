package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceCrudService extends GenericCrudService<Device, Integer> {
    public DeviceCrudService(DeviceRepository repo) {
        super(repo);
    }
}

