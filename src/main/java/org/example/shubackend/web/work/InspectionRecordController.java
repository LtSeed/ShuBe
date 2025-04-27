package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.InspectionRecord;
import org.example.shubackend.service.work.InspectionRecordCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inspection-records")
@RequiredArgsConstructor
class InspectionRecordController {
    private final InspectionRecordCrudService svc;

    @PreAuthorize("hasPermission(null,'INSPECTION_RECORDS_READ')")
    @GetMapping
    public List<InspectionRecord> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_RECORDS_READ')")
    @GetMapping("/{id}")
    public Optional<InspectionRecord> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_RECORDS_CREATE')")
    @PostMapping
    public InspectionRecord create(@RequestBody InspectionRecord d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_RECORDS_UPDATE')")
    @PutMapping("/{id}")
    public InspectionRecord upd(@PathVariable Integer id, @RequestBody InspectionRecord d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_RECORDS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
