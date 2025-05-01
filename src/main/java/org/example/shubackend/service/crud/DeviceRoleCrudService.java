package org.example.shubackend.service.crud;

import org.example.shubackend.dto.device.DeviceRoleDTO;
import org.example.shubackend.dtomapper.device.DeviceRoleMapper;
import org.example.shubackend.entity.work.device.DeviceRole;
import org.example.shubackend.repository.DeviceRoleRepository;
import org.example.shubackend.dtomapper.device.SimpleDeviceRoleMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceRoleCrudService extends GenericCrudService<DeviceRole, Integer> {

    private final DeviceRoleRepository repo;
    private final DeviceRoleMapper mapper;

    protected DeviceRoleCrudService(JpaRepository<DeviceRole, Integer> repo, DeviceRoleRepository repo1, DeviceRoleMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<DeviceRoleDTO> findAllDto() {
        return repo.findAll()
                   .stream()
                   .map(mapper::toDto)
                   .collect(Collectors.toList());
    }

    public Optional<DeviceRoleDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public DeviceRoleDTO createDto(DeviceRoleDTO dto) {
        DeviceRole saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Transactional
    public DeviceRoleDTO updateDto(Integer id, DeviceRoleDTO dto) {
        DeviceRole e = mapper.toEntity(dto);
        e.setId(id);
        DeviceRole saved = repo.save(e);
        return mapper.toDto(saved);
    }
}
