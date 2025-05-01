package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.emergency.EmergencyDTO;
import org.example.shubackend.service.crud.EmergencyCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emergencies")
@RequiredArgsConstructor
public class EmergencyController {
    private final EmergencyCrudService svc;

    @PreAuthorize("hasPermission(null,'EMERGENCY_READ')")
    @GetMapping
    public List<EmergencyDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_READ')")
    @GetMapping("/{id}")
    public Optional<EmergencyDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_CREATE')")
    @PostMapping
    public EmergencyDTO create(@RequestBody EmergencyDTO d) {
        return svc.createDto(d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_UPDATE')")
    @PutMapping("/{id}")
    public EmergencyDTO upd(@PathVariable Integer id, @RequestBody EmergencyDTO d) {
        return svc.updateDto(id, d);
    }

    @PreAuthorize("hasPermission(null,'EMERGENCY_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}