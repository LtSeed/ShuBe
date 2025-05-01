package org.example.shubackend.service.crud;

import jakarta.transaction.Transactional;
import org.example.shubackend.dto.perm.RoleDTO;
import org.example.shubackend.dtomapper.perm.RoleMapper;
import org.example.shubackend.entity.Role;
import org.example.shubackend.repository.RoleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleCrudService extends GenericCrudService<Role, Integer> {

    private final RoleRepository repo;
    private final RoleMapper mapper;

    protected RoleCrudService(JpaRepository<Role, Integer> repo, RoleRepository repo1, RoleMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<RoleDTO> findAllDto() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public Optional<RoleDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public RoleDTO createDto(RoleDTO d) {
        return mapper.toDto(repo.save(mapper.toEntity(d)));
    }

    @Transactional
    public RoleDTO updateDto(Integer id, RoleDTO d) {
        Role r = mapper.toEntity(d);
        r.setId(id);
        return mapper.toDto(repo.save(r));
    }
}
