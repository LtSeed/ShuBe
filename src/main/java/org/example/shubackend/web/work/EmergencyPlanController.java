package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.EmergencyPlan;
import org.example.shubackend.service.work.EmergencyPlanCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emergency-plans")
@RequiredArgsConstructor
class EmergencyPlanController {
    private final EmergencyPlanCrudService svc;

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLANS_READ')")
    @GetMapping
    public List<EmergencyPlan> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLANS_READ')")
    @GetMapping("/{id}")
    public Optional<EmergencyPlan> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLANS_CREATE')")
    @PostMapping
    public EmergencyPlan create(@RequestBody EmergencyPlan d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLANS_UPDATE')")
    @PutMapping("/{id}")
    public EmergencyPlan upd(@PathVariable Integer id, @RequestBody EmergencyPlan d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLANS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
