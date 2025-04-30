package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.repair.RepairOrder;
import org.example.shubackend.service.crud.RepairOrderCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/repair-orders")
@RequiredArgsConstructor
class RepairOrderController {
    private final RepairOrderCrudService svc;

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_READ')")
    @GetMapping
    public List<RepairOrder> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_READ')")
    @GetMapping("/{id}")
    public Optional<RepairOrder> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_CREATE')")
    @PostMapping
    public RepairOrder create(@RequestBody RepairOrder d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_UPDATE')")
    @PutMapping("/{id}")
    public RepairOrder upd(@PathVariable Integer id, @RequestBody RepairOrder d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}