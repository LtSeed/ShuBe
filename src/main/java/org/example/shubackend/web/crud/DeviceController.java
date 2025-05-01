package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.device.DeviceDTO;
import org.example.shubackend.service.crud.DeviceCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceCrudService svc;

    @PreAuthorize("hasPermission(null,'DEVICES_READ')")
    @GetMapping
    public List<DeviceDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'DEVICES_READ')")
    @GetMapping("/{id}")
    public Optional<DeviceDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'DEVICES_CREATE')")
    @PostMapping
    public DeviceDTO create(@RequestBody DeviceDTO dto) {
        return svc.createDto(dto);
    }

    @PreAuthorize("hasPermission(null,'DEVICES_UPDATE')")
    @PutMapping("/{id}")
    public DeviceDTO update(@PathVariable Integer id, @RequestBody DeviceDTO dto) {
        return svc.updateDto(id, dto);
    }

    @PreAuthorize("hasPermission(null,'DEVICES_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}
