package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.DeviceRole;
import org.example.shubackend.service.crud.DeviceRoleCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/device-roles")
@RequiredArgsConstructor
class DeviceRoleController {
    private final DeviceRoleCrudService svc;

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_READ')")
    @GetMapping
    public List<DeviceRole> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_READ')")
    @GetMapping("/{id}")
    public Optional<DeviceRole> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_CREATE')")
    @PostMapping
    public DeviceRole create(@RequestBody DeviceRole d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_UPDATE')")
    @PutMapping("/{id}")
    public DeviceRole upd(@PathVariable Integer id, @RequestBody DeviceRole d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
