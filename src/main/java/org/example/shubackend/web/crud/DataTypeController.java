package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.common.DataType;
import org.example.shubackend.service.crud.DataTypeCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data-types")
@RequiredArgsConstructor
public class DataTypeController {
    private final DataTypeCrudService svc;

    @PreAuthorize("hasPermission(null,'DATA_TYPES_READ')")
    @GetMapping
    public List<DataType> list() {
        return svc.findAll();
    }

    @PreAuthorize("hasPermission(null,'DATA_TYPES_READ')")
    @GetMapping("/{id}")
    public Optional<DataType> one(@PathVariable Integer id) {
        return svc.find(id);
    }

    @PreAuthorize("hasPermission(null,'DATA_TYPES_CREATE')")
    @PostMapping
    public DataType create(@RequestBody DataType d) {
        return svc.create(d);
    }

    @PreAuthorize("hasPermission(null,'DATA_TYPES_UPDATE')")
    @PutMapping("/{id}")
    public DataType upd(@PathVariable Integer id, @RequestBody DataType d) {
        return svc.update(id, d);
    }

    @PreAuthorize("hasPermission(null,'DATA_TYPES_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}

