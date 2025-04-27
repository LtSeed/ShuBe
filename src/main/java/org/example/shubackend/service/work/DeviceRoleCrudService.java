package org.example.shubackend.service.work;

import org.example.shubackend.repository.DeviceRoleRepository;
import org.example.shubackend.entity.work.DeviceRole;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class DeviceRoleCrudService extends GenericCrudService<DeviceRole, Integer> {
    public DeviceRoleCrudService(DeviceRoleRepository r) {
        super(r);
    }
}
