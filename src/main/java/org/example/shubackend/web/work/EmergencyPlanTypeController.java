package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.EmergencyPlanType;
import org.example.shubackend.service.work.EmergencyPlanTypeCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emergency-plan-types")
@RequiredArgsConstructor
class EmergencyPlanTypeController {
    private final EmergencyPlanTypeCrudService svc;

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_TYPES_READ')")
    @GetMapping
    public List<EmergencyPlanType> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_TYPES_READ')")
    @GetMapping("/{id}")
    public Optional<EmergencyPlanType> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_TYPES_CREATE')")
    @PostMapping
    public EmergencyPlanType create(@RequestBody EmergencyPlanType d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_TYPES_UPDATE')")
    @PutMapping("/{id}")
    public EmergencyPlanType upd(@PathVariable Integer id, @RequestBody EmergencyPlanType d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_TYPES_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
