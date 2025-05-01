package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.device.DeviceEventLogDTO;
import org.example.shubackend.service.crud.DeviceEventLogCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
/* DeviceEventLogController */
@RequestMapping("/device-event-logs")
public class DeviceEventLogController {
    private final DeviceEventLogCrudService svc;

    @PreAuthorize("hasPermission(null,'DEVICE_EVENT_LOGS_READ')")
    @GetMapping
    public List<DeviceEventLogDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'DEVICE_EVENT_LOGS_READ')")
    @GetMapping("/{id}")
    public Optional<DeviceEventLogDTO> one(@PathVariable Long id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'DEVICE_EVENT_LOGS_CREATE')")
    @PostMapping
    public DeviceEventLogDTO create(@RequestBody DeviceEventLogDTO d) {
        return svc.createDto(d);
    }
}

