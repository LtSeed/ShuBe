/* EmergencyLogController.java */
package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.emergency.EmergencyLogDTO;
import org.example.shubackend.service.crud.EmergencyLogCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emergency-logs")
@RequiredArgsConstructor
public class EmergencyLogController {

    private final EmergencyLogCrudService svc;

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_READ')")
    @GetMapping
    public List<EmergencyLogDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_READ')")
    @GetMapping("/{id}")
    public Optional<EmergencyLogDTO> one(@PathVariable Long id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_LOGS_CREATE')")
    @PostMapping
    public EmergencyLogDTO create(@RequestBody EmergencyLogDTO dto) {
        return svc.createDto(dto);
    }
}
