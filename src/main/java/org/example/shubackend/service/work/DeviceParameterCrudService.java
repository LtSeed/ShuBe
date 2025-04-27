package org.example.shubackend.service.work;

import org.example.shubackend.repository.DeviceParameterRepository;
import org.example.shubackend.entity.work.DeviceParameter;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class DeviceParameterCrudService extends GenericCrudService<DeviceParameter, Integer> {
    public DeviceParameterCrudService(DeviceParameterRepository r) {
        super(r);
    }
}
