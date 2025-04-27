package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.SparePart;
import org.example.shubackend.service.crud.SparePartCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spare-parts")
@RequiredArgsConstructor
class SparePartController {
    private final SparePartCrudService svc;

    @PreAuthorize("hasPermission(null,'SPARE_PARTS_READ')")
    @GetMapping
    public List<SparePart> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'SPARE_PARTS_READ')")
    @GetMapping("/{id}")
    public Optional<SparePart> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'SPARE_PARTS_CREATE')")
    @PostMapping
    public SparePart create(@RequestBody SparePart d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'SPARE_PARTS_UPDATE')")
    @PutMapping("/{id}")
    public SparePart upd(@PathVariable Integer id, @RequestBody SparePart d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'SPARE_PARTS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}


