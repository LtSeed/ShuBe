package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.perm.RoleDTO;
import org.example.shubackend.service.crud.RoleCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/* RoleController (DTO 版) */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleCrudService svc;

    @PreAuthorize("hasPermission(null,'ROLES_READ')")
    @GetMapping
    public List<RoleDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'ROLES_READ')")
    @GetMapping("/{id}")
    public Optional<RoleDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'ROLES_CREATE')")
    @PostMapping
    public RoleDTO create(@RequestBody RoleDTO d) {
        return svc.createDto(d);
    }

    @PreAuthorize("hasPermission(null,'ROLES_UPDATE')")
    @PutMapping("/{id}")
    public RoleDTO upd(@PathVariable Integer id, @RequestBody RoleDTO d) {
        return svc.updateDto(id, d);
    }

    @PreAuthorize("hasPermission(null,'ROLES_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
