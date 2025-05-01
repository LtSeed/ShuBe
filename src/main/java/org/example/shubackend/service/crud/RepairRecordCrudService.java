// RepairRecordCrudService.java
package org.example.shubackend.service.crud;

import org.example.shubackend.dto.repair.RepairRecordDTO;
import org.example.shubackend.entity.work.repair.RepairRecord;
import org.example.shubackend.repository.RepairRecordRepository;
import org.example.shubackend.dtomapper.repair.RepairRecordMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepairRecordCrudService
        extends GenericCrudService<RepairRecord, Integer> {

    private final RepairRecordRepository repo;
    private final RepairRecordMapper mapper;

    protected RepairRecordCrudService(JpaRepository<RepairRecord, Integer> repo, RepairRecordRepository repo1, RepairRecordMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<RepairRecordDTO> findAllDto() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<RepairRecordDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public RepairRecordDTO createDto(RepairRecordDTO dto) {
        RepairRecord saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Transactional
    public RepairRecordDTO updateDto(Integer id, RepairRecordDTO dto) {
        RepairRecord e = mapper.toEntity(dto);
        e.setId(id);
        RepairRecord saved = repo.save(e);
        return mapper.toDto(saved);
    }
}
