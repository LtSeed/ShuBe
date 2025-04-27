package org.example.shubackend.web.crud;

import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.service.crud.DeviceCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceCrudService svc;

    public DeviceController(DeviceCrudService svc) {
        this.svc = svc;
    }

    @PreAuthorize("hasPermission(null,'DEVICES_READ')")
    @GetMapping
    public java.util.List<Device> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'DEVICES_READ')")
    @GetMapping("/{id}")
    public java.util.Optional<Device> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'DEVICES_CREATE')")
    @PostMapping
    public Device create(@RequestBody Device d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'DEVICES_UPDATE')")
    @PutMapping("/{id}")
    public Device update(@PathVariable Integer id, @RequestBody Device d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'DEVICES_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}

