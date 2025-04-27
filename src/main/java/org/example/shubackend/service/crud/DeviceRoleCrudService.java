package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.DeviceRole;
import org.example.shubackend.repository.DeviceRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceRoleCrudService extends GenericCrudService<DeviceRole, Integer> {
    public DeviceRoleCrudService(DeviceRoleRepository r) {
        super(r);
    }
}
