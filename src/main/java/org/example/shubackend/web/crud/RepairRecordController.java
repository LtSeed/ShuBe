// RepairRecordController.java
package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.repair.RepairRecordDTO;
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

    @PreAuthorize("hasPermission(principal, 'REPAIR_RECORDS_READ')")
    @GetMapping
    public List<RepairRecordDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_RECORDS_READ')")
    @GetMapping("/{id}")
    public Optional<RepairRecordDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_RECORDS_CREATE')")
    @PostMapping
    public RepairRecordDTO create(@RequestBody RepairRecordDTO dto) {
        return svc.createDto(dto);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_RECORDS_UPDATE')")
    @PutMapping("/{id}")
    public RepairRecordDTO upd(
            @PathVariable Integer id,
            @RequestBody RepairRecordDTO dto
    ) {
        return svc.updateDto(id, dto);
    }

    @PreAuthorize("hasPermission(principal, 'REPAIR_RECORDS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
