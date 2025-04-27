package org.example.shubackend.web;

import org.example.shubackend.entity.Permission;
import org.example.shubackend.repository.PermissionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    private final PermissionRepository repo;

    PermissionController(PermissionRepository r) {
        this.repo = r;
    }

    @PreAuthorize("hasPermission(null,'PERMISSIONS_READ')")
    @GetMapping
    public List<Permission> list() {
        return repo.findAll();
    }
}
