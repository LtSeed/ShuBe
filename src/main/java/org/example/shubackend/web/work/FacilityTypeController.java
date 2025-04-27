package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.FacilityType;
import org.example.shubackend.service.work.FacilityTypeCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facility-types")
@RequiredArgsConstructor
public class FacilityTypeController {
    private final FacilityTypeCrudService svc;

    @PreAuthorize("hasPermission(null,'FACILITY_TYPES_READ')")
    @GetMapping
    public List<FacilityType> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'FACILITY_TYPES_READ')")
    @GetMapping("/{id}")
    public Optional<FacilityType> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'FACILITY_TYPES_CREATE')")
    @PostMapping
    public FacilityType create(@RequestBody FacilityType d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'FACILITY_TYPES_UPDATE')")
    @PutMapping("/{id}")
    public FacilityType upd(@PathVariable Integer id, @RequestBody FacilityType d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'FACILITY_TYPES_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
