package org.example.shubackend.service.crud;

import org.example.shubackend.dto.inspection.InspectionRecordDTO;
import org.example.shubackend.entity.work.inspection.InspectionRecord;
import org.example.shubackend.repository.InspectionRecordRepository;
import org.example.shubackend.dtomapper.inspection.InspectionRecordMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InspectionRecordCrudService
        extends GenericCrudService<InspectionRecord, Integer> {

    private final InspectionRecordRepository repo;
    private final InspectionRecordMapper mapper;

    protected InspectionRecordCrudService(JpaRepository<InspectionRecord, Integer> repo, InspectionRecordRepository repo1, InspectionRecordMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    /* ===== DTO API ===== */

    public List<InspectionRecordDTO> findAllDto() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<InspectionRecordDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    public InspectionRecordDTO createDto(InspectionRecordDTO dto) {
        InspectionRecord saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    public InspectionRecordDTO updateDto(Integer id, InspectionRecordDTO dto) {
        InspectionRecord entity = mapper.toEntity(dto);
        entity.setId(id);
        InspectionRecord saved = repo.save(entity);
        return mapper.toDto(saved);
    }
}
