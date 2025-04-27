package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.MaintenanceOrder;
import org.example.shubackend.service.work.MaintenanceOrderCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maintenance-orders")
@RequiredArgsConstructor
class MaintenanceOrderController {
    private final MaintenanceOrderCrudService svc;

    @PreAuthorize("hasPermission(null,'MAINTENANCE_ORDERS_READ')")
    @GetMapping
    public List<MaintenanceOrder> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_ORDERS_READ')")
    @GetMapping("/{id}")
    public Optional<MaintenanceOrder> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_ORDERS_CREATE')")
    @PostMapping
    public MaintenanceOrder create(@RequestBody MaintenanceOrder d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_ORDERS_UPDATE')")
    @PutMapping("/{id}")
    public MaintenanceOrder upd(@PathVariable Integer id, @RequestBody MaintenanceOrder d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'MAINTENANCE_ORDERS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
