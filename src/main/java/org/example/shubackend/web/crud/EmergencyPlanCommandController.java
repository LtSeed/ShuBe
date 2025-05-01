/* EmergencyPlanCommandController.java */
package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.emergency.EmergencyPlanCommandDTO;
import org.example.shubackend.service.crud.EmergencyPlanCommandCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emergency-plan-commands")
@RequiredArgsConstructor
public class EmergencyPlanCommandController {

    private final EmergencyPlanCommandCrudService svc;

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_READ')")
    @GetMapping
    public List<EmergencyPlanCommandDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_READ')")
    @GetMapping("/{id}")
    public Optional<EmergencyPlanCommandDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_CREATE')")
    @PostMapping
    public EmergencyPlanCommandDTO create(@RequestBody EmergencyPlanCommandDTO dto) {
        return svc.createDto(dto);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_UPDATE')")
    @PutMapping("/{id}")
    public EmergencyPlanCommandDTO update(@PathVariable Integer id, @RequestBody EmergencyPlanCommandDTO dto) {
        return svc.updateDto(id, dto);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_PLAN_COMMANDS_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}
