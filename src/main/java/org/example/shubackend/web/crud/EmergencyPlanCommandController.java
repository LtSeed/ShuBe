package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.emergency.EmergencyPlanCommand;
import org.example.shubackend.service.crud.EmergencyPlanCommandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emergency-plan-commands")
@RequiredArgsConstructor
public class EmergencyPlanCommandController {

    private final EmergencyPlanCommandService svc;

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_READ')")
    @GetMapping
    public List<EmergencyPlanCommand> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_READ')")
    @GetMapping("/{id}")
    public Optional<EmergencyPlanCommand> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_CREATE')")
    @PostMapping
    public EmergencyPlanCommand create(@RequestBody EmergencyPlanCommand c) {
        return svc.create(c);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_UPDATE')")
    @PutMapping("/{id}")
    public EmergencyPlanCommand upd(@PathVariable Integer id, @RequestBody EmergencyPlanCommand c) {
        return svc.update(id, c);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
