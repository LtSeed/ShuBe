package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.MaintenanceRecord;
import org.example.shubackend.service.work.MaintenanceRecordCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maintenance-records")
@RequiredArgsConstructor
class MaintenanceRecordController {
    private final MaintenanceRecordCrudService svc;

    @PreAuthorize("hasPermission(null,'MAINTENANCE_RECORDS_READ')")
    @GetMapping
    public List<MaintenanceRecord> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_RECORDS_READ')")
    @GetMapping("/{id}")
    public Optional<MaintenanceRecord> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_RECORDS_CREATE')")
    @PostMapping
    public MaintenanceRecord create(@RequestBody MaintenanceRecord d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_RECORDS_UPDATE')")
    @PutMapping("/{id}")
    public MaintenanceRecord upd(@PathVariable Integer id, @RequestBody MaintenanceRecord d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_RECORDS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
