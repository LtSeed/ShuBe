package org.example.shubackend.service.crud;

import jakarta.transaction.Transactional;
import org.example.shubackend.dto.emergency.EmergencyPlanCommandDTO;
import org.example.shubackend.dtomapper.emergency.EmergencyPlanCommandMapper;
import org.example.shubackend.entity.work.device.emergency.EmergencyPlanCommand;
import org.example.shubackend.repository.EmergencyPlanCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyPlanCommandCrudService extends GenericCrudService<EmergencyPlanCommand, Integer> {
    private final EmergencyPlanCommandRepository repo;
    private final EmergencyPlanCommandMapper map;

    protected EmergencyPlanCommandCrudService(JpaRepository<EmergencyPlanCommand, Integer> repo, EmergencyPlanCommandRepository repo1, EmergencyPlanCommandMapper map) {
        super(repo);
        this.repo = repo1;
        this.map = map;
    }

    public List<EmergencyPlanCommandDTO> findAllDto() {
        return repo.findAll().stream().map(map::toDto).toList();
    }

    public Optional<EmergencyPlanCommandDTO> findDto(Integer id) {
        return repo.findById(id).map(map::toDto);
    }

    @Transactional
    public EmergencyPlanCommandDTO createDto(EmergencyPlanCommandDTO d) {
        return map.toDto(repo.save(map.toEntity(d)));
    }

    @Transactional
    public EmergencyPlanCommandDTO updateDto(Integer id, EmergencyPlanCommandDTO d) {
        var e = map.toEntity(d);
        e.setId(id);
        return map.toDto(repo.save(e));
    }
}