// RepairOrderController.java
package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.repair.RepairOrderDTO;
import org.example.shubackend.service.crud.RepairOrderCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/repair-orders")
@RequiredArgsConstructor
public class RepairOrderController {

    private final RepairOrderCrudService svc;

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_READ')")
    @GetMapping
    public List<RepairOrderDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_READ')")
    @GetMapping("/{id}")
    public Optional<RepairOrderDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_CREATE')")
    @PostMapping
    public RepairOrderDTO create(@RequestBody RepairOrderDTO dto) {
        return svc.createDto(dto);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_UPDATE')")
    @PutMapping("/{id}")
    public RepairOrderDTO upd(
            @PathVariable Integer id,
            @RequestBody RepairOrderDTO dto
    ) {
        return svc.updateDto(id, dto);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_ORDERS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
