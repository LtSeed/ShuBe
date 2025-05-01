// RepairOrderCrudService.java
package org.example.shubackend.service.crud;

import org.example.shubackend.dto.repair.RepairOrderDTO;
import org.example.shubackend.entity.work.repair.RepairOrder;
import org.example.shubackend.repository.RepairOrderRepository;
import org.example.shubackend.dtomapper.repair.RepairOrderMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepairOrderCrudService
        extends GenericCrudService<RepairOrder, Integer> {

    private final RepairOrderRepository repo;
    private final RepairOrderMapper mapper;

    protected RepairOrderCrudService(JpaRepository<RepairOrder, Integer> repo, RepairOrderRepository repo1, RepairOrderMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<RepairOrderDTO> findAllDto() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<RepairOrderDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public RepairOrderDTO createDto(RepairOrderDTO dto) {
        RepairOrder saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Transactional
    public RepairOrderDTO updateDto(Integer id, RepairOrderDTO dto) {
        RepairOrder e = mapper.toEntity(dto);
        e.setId(id);
        RepairOrder saved = repo.save(e);
        return mapper.toDto(saved);
    }
}
