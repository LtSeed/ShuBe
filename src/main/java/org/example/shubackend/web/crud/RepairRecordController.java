package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.repair.RepairRecord;
import org.example.shubackend.service.crud.RepairRecordCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/repair-records")
@RequiredArgsConstructor
public class RepairRecordController {
    private final RepairRecordCrudService svc;

    @PreAuthorize("hasPermission(null,'Repair_RECORDS_READ')")
    @GetMapping
    public List<RepairRecord> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'REPAIR_RECORDS_READ')")
    @GetMapping("/{id}")
    public Optional<RepairRecord> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'REPAIR_RECORDS_CREATE')")
    @PostMapping
    public RepairRecord create(@RequestBody RepairRecord d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'REPAIR_RECORDS_UPDATE')")
    @PutMapping("/{id}")
    public RepairRecord upd(@PathVariable Integer id, @RequestBody RepairRecord d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'REPAIR_RECORDS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
