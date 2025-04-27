package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.event.DeviceEventLog;
import org.example.shubackend.service.crud.EventLogCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event-logs")
@RequiredArgsConstructor
public class EventLogController {
    private final EventLogCrudService svc;

    @PreAuthorize("hasPermission(null,'EVENT_LOGS_READ')")
    @GetMapping
    public List<DeviceEventLog> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'EVENT_LOGS_READ')")
    @GetMapping("/{id}")
    public Optional<DeviceEventLog> one(@PathVariable Long id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'EVENT_LOGS_CREATE')")
    @PostMapping
    public DeviceEventLog create(@RequestBody DeviceEventLog d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'EVENT_LOGS_UPDATE')")
    @PutMapping("/{id}")
    public DeviceEventLog upd(@PathVariable Long id, @RequestBody DeviceEventLog d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'EVENT_LOGS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Long id) {
        svc.delete(id);
    }
}
