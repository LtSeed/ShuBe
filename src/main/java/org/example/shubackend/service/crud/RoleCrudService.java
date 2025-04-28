package org.example.shubackend.service.crud;

import org.example.shubackend.entity.Role;
import org.example.shubackend.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleCrudService extends GenericCrudService<Role, Integer> {
    public RoleCrudService(RoleRepository repo) {
        super(repo);
    }
}