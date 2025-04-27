package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.DeviceParameter;
import org.example.shubackend.service.work.DeviceParameterCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// ---------- DeviceParameter ----------
@RestController
@RequestMapping("/device-parameters")
@RequiredArgsConstructor
public class DeviceParameterController {
    private final DeviceParameterCrudService svc;

    @PreAuthorize("hasPermission(null,'DEVICE_PARAMETERS_READ')")
    @GetMapping
    public List<DeviceParameter> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'DEVICE_PARAMETERS_READ')")
    @GetMapping("/{id}")
    public Optional<DeviceParameter> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_PARAMETERS_CREATE')")
    @PostMapping
    public DeviceParameter create(@RequestBody DeviceParameter d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_PARAMETERS_UPDATE')")
    @PutMapping("/{id}")
    public DeviceParameter upd(@PathVariable Integer id, @RequestBody DeviceParameter d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_PARAMETERS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
