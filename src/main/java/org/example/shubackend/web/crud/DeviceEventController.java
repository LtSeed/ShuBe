/* DeviceEventController */
package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.device.DeviceEventDTO;
import org.example.shubackend.service.crud.DeviceEventCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/device-events")
@RequiredArgsConstructor
public class DeviceEventController {
    private final DeviceEventCrudService svc;

    @PreAuthorize("hasPermission(null,'DEVICE_EVENTS_READ')")
    @GetMapping
    public List<DeviceEventDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'DEVICE_EVENTS_READ')")
    @GetMapping("/{id}")
    public Optional<DeviceEventDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_EVENTS_CREATE')")
    @PostMapping
    public DeviceEventDTO create(@RequestBody DeviceEventDTO d) {
        return svc.createDto(d);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_EVENTS_UPDATE')")
    @PutMapping("/{id}")
    public DeviceEventDTO upd(@PathVariable Integer id, @RequestBody DeviceEventDTO d) {
        return svc.updateDto(id, d);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_EVENTS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
