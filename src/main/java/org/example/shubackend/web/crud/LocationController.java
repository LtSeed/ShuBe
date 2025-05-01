package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.device.LocationDTO;
import org.example.shubackend.service.crud.LocationCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationCrudService svc;

    @PreAuthorize("hasPermission(null,'LOCATIONS_READ')")
    @GetMapping
    public List<LocationDTO> list() {
        return svc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_READ')")
    @GetMapping("/{id}")
    public Optional<LocationDTO> one(@PathVariable Integer id) {
        return svc.findDto(id);
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_CREATE')")
    @PostMapping
    public LocationDTO create(@RequestBody LocationDTO dto) {
        return svc.createDto(dto);
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_UPDATE')")
    @PutMapping("/{id}")
    public LocationDTO update(@PathVariable Integer id, @RequestBody LocationDTO dto) {
        return svc.updateDto(id, dto);
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        svc.delete(id);
    }
}
