package org.example.shubackend.web.work;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.InspectionPlan;
import org.example.shubackend.service.work.InspectionPlanCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inspection-plans")
@RequiredArgsConstructor
class InspectionPlanController {
    private final InspectionPlanCrudService svc;

    @PreAuthorize("hasPermission(null,'INSPECTION_PLANS_READ')")
    @GetMapping
    public List<InspectionPlan> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_PLANS_READ')")
    @GetMapping("/{id}")
    public Optional<InspectionPlan> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_PLANS_CREATE')")
    @PostMapping
    public InspectionPlan create(@RequestBody InspectionPlan d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_PLANS_UPDATE')")
    @PutMapping("/{id}")
    public InspectionPlan upd(@PathVariable Integer id, @RequestBody InspectionPlan d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'INSPECTION_PLANS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
