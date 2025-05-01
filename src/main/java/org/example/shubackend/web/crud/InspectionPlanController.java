package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.dto.inspection.InspectionPlanDTO;
import org.example.shubackend.dtomapper.inspection.InspectionPlanMapper;
import org.example.shubackend.service.crud.InspectionPlanCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/inspection-plans")
@RequiredArgsConstructor
class InspectionPlanController {
    private final InspectionPlanCrudService svc;
    private final InspectionPlanMapper mapper;


    @PreAuthorize("hasPermission(principal,'INSPECTION_PLANS_READ')")
    @GetMapping
    public List<InspectionPlanDTO> list() {
        List<InspectionPlanDTO> dtoList = mapper.toDtoList(svc.findAll());
        log.info("dtoList: {}", dtoList);
        return dtoList;
    }

    @PreAuthorize("hasPermission(principal,'INSPECTION_PLANS_READ')")
    @GetMapping("/{id}")
    public InspectionPlanDTO one(@PathVariable Integer id) {
        return mapper.toDto(svc.find(id)
                .orElseThrow(() -> new RuntimeException("not found")));
    }

    @PreAuthorize("hasPermission(principal,'INSPECTION_PLANS_CREATE')")
    @PostMapping
    public InspectionPlanDTO create(@RequestBody InspectionPlanDTO dto) {
        return mapper.toDto(svc.createByDto(dto));
    }

    @PreAuthorize("hasPermission(principal,'INSPECTION_PLANS_UPDATE')")
    @PutMapping("/{id}")
    public InspectionPlanDTO update(@PathVariable Integer id,
                                    @RequestBody InspectionPlanDTO dto) {
        return mapper.toDto(svc.updateByDto(id, dto));
    }

    @PreAuthorize("hasPermission(principal,'INSPECTION_PLANS_DELETE')")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        svc.delete(id);
    }
}
