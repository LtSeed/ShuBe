package org.example.shubackend.service.crud;

import jakarta.transaction.Transactional;
import org.example.shubackend.dto.emergency.EmergencyLogDTO;
import org.example.shubackend.dtomapper.emergency.EmergencyLogMapper;
import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.example.shubackend.repository.EmergencyLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyLogCrudService extends GenericCrudService<EmergencyLog, Long> {
    private final EmergencyLogRepository repo;
    private final EmergencyLogMapper map;

    protected EmergencyLogCrudService(JpaRepository<EmergencyLog, Long> repo, EmergencyLogRepository repo1, EmergencyLogMapper map) {
        super(repo);
        this.repo = repo1;
        this.map = map;
    }

    public List<EmergencyLogDTO> findAllDto() {
        return repo.findAll().stream().map(map::toDto).toList();
    }

    public Optional<EmergencyLogDTO> findDto(Long id) {
        return repo.findById(id).map(map::toDto);
    }

    @Transactional
    public EmergencyLogDTO createDto(EmergencyLogDTO d) {
        return map.toDto(repo.save(map.toEntity(d)));
    }
}
