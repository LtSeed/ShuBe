package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.inspection.InspectionRecordDTO;
import org.example.shubackend.service.crud.InspectionRecordCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inspection-records")
@RequiredArgsConstructor
class InspectionRecordController {

    private final InspectionRecordCrudService svc;

    /* -------- READ -------- */

    @PreAuthorize("hasPermission(principal,'INSPECTION_RECORDS_READ')")
    @GetMapping
    public List<InspectionRecordDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(principal,'INSPECTION_RECORDS_READ')")
    @GetMapping("/{id}")
    public Optional<InspectionRecordDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    /* -------- CREATE -------- */

    @PreAuthorize("hasPermission(principal,'INSPECTION_RECORDS_CREATE')")
    @PostMapping
    public InspectionRecordDTO create(@RequestBody InspectionRecordDTO body) {
        return svc.createDto(body);
    }

    /* -------- UPDATE -------- */

    @PreAuthorize("hasPermission(principal,'INSPECTION_RECORDS_UPDATE')")
    @PutMapping("/{id}")
    public InspectionRecordDTO upd(@PathVariable Integer id,
                                   @RequestBody InspectionRecordDTO body) {
        return svc.updateDto(id, body);
    }

    /* -------- DELETE -------- */

    @PreAuthorize("hasPermission(principal,'INSPECTION_RECORDS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
