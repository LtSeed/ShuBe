package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.Location;
import org.example.shubackend.service.crud.LocationCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
class LocationController {
    private final LocationCrudService svc;

    @PreAuthorize("hasPermission(null,'LOCATIONS_READ')")
    @GetMapping
    public List<Location> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_READ')")
    @GetMapping("/{id}")
    public Optional<Location> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_CREATE')")
    @PostMapping
    public Location create(@RequestBody Location d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_UPDATE')")
    @PutMapping("/{id}")
    public Location upd(@PathVariable Integer id, @RequestBody Location d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'LOCATIONS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
