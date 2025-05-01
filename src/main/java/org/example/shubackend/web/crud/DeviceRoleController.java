package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.device.DeviceRoleDTO;
import org.example.shubackend.service.crud.DeviceRoleCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/device-roles")
@RequiredArgsConstructor
public class DeviceRoleController {

    private final DeviceRoleCrudService svc;

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_READ')")
    @GetMapping
    public List<DeviceRoleDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_READ')")
    @GetMapping("/{id}")
    public Optional<DeviceRoleDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_CREATE')")
    @PostMapping
    public DeviceRoleDTO create(@RequestBody DeviceRoleDTO dto) {
        return svc.createDto(dto);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_UPDATE')")
    @PutMapping("/{id}")
    public DeviceRoleDTO update(@PathVariable Integer id, @RequestBody DeviceRoleDTO dto) {
        return svc.updateDto(id, dto);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_ROLES_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}
