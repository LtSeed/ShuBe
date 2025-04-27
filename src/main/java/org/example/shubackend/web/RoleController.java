package org.example.shubackend.web;

import org.example.shubackend.entity.Role;
import org.example.shubackend.repository.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleRepository repo;

    public RoleController(RoleRepository repo) {
        this.repo = repo;
    }

    @PreAuthorize("hasPermission(null,'ROLES_READ')")
    @GetMapping
    public List<Role> list() {
        return repo.findAll();
    }

    @PreAuthorize("hasPermission(null,'ROLES_CREATE')")
    @PostMapping
    public Role create(@RequestBody Role r) {
        return repo.save(r);
    }

    @PreAuthorize("hasPermission(null,'ROLES_UPDATE')")
    @PutMapping("/{id}")
    public Role update(@PathVariable Integer id, @RequestBody Role r) {
        r.setId(id);
        return repo.save(r);
    }

    @PreAuthorize("hasPermission(null,'ROLES_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}

