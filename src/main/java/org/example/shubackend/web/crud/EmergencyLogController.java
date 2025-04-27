package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.example.shubackend.service.crud.EmergencyLogCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emergency-logs")
@RequiredArgsConstructor
public class EmergencyLogController {
    private final EmergencyLogCrudService svc;

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_READ')")
    @GetMapping
    public List<EmergencyLog> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_READ')")
    @GetMapping("/{id}")
    public Optional<EmergencyLog> one(@PathVariable Long id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_CREATE')")
    @PostMapping
    public EmergencyLog create(@RequestBody EmergencyLog d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_UPDATE')")
    @PutMapping("/{id}")
    public EmergencyLog upd(@PathVariable Long id, @RequestBody EmergencyLog d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Long id) {
        svc.delete(id);
    }
}


