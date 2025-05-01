package org.example.shubackend.service.crud;

import jakarta.transaction.Transactional;
import org.example.shubackend.dto.emergency.EmergencyDTO;
import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.repository.EmergencyRepository;
import org.example.shubackend.dtomapper.emergency.EmergencyMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyCrudService extends GenericCrudService<Emergency, Integer> {
    private final EmergencyRepository repo;
    private final EmergencyMapper map;

    protected EmergencyCrudService(JpaRepository<Emergency, Integer> repo, EmergencyRepository repo1, EmergencyMapper map) {
        super(repo);
        this.repo = repo1;
        this.map = map;
    }

    public List<EmergencyDTO> findAllDto() {
        return repo.findAll().stream().map(map::toDto).toList();
    }

    public Optional<EmergencyDTO> findDto(Integer id) {
        return repo.findById(id).map(map::toDto);
    }

    @Transactional
    public EmergencyDTO createDto(EmergencyDTO d) {
        return map.toDto(repo.save(map.toEntity(d)));
    }

    @Transactional
    public EmergencyDTO updateDto(Integer id, EmergencyDTO d) {
        var e = map.toEntity(d);
        e.setId(id);
        return map.toDto(repo.save(e));
    }
}