package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.emergency.AreaEmergencyLog;
import org.example.shubackend.service.crud.AreaEmergencyLogCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/area-emergency-logs")
@RequiredArgsConstructor
public class AreaEmergencyLogController {

    private final AreaEmergencyLogCrudService svc;

    @PreAuthorize("hasPermission(null,'AREA_EMERGENCY_LOGS_READ')")
    @GetMapping
    public List<AreaEmergencyLog> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'AREA_EMERGENCY_LOGS_READ')")
    @GetMapping("/{id}")
    public Optional<AreaEmergencyLog> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'AREA_EMERGENCY_LOGS_CREATE')")
    @PostMapping
    public AreaEmergencyLog create(@RequestBody AreaEmergencyLog log) {
        return svc.create(log);
    }

    @PreAuthorize("hasPermission(null,'AREA_EMERGENCY_LOGS_UPDATE')")
    @PutMapping("/{id}")
    public AreaEmergencyLog update(@PathVariable Integer id,
                                   @RequestBody AreaEmergencyLog log) {
        return svc.update(id, log);
    }

    @PreAuthorize("hasPermission(null,'AREA_EMERGENCY_LOGS_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}
