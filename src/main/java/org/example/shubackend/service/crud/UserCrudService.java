package org.example.shubackend.service.crud;

import jakarta.transaction.Transactional;
import org.example.shubackend.dto.perm.UserWithRolesDTO;
import org.example.shubackend.dtomapper.UserMapper;
import org.example.shubackend.entity.User;
import org.example.shubackend.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCrudService extends GenericCrudService<User, Integer> {

    private final UserRepository repo;
    private final UserMapper mapper;

    protected UserCrudService(JpaRepository<User, Integer> repo, UserRepository repo1, UserMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<UserWithRolesDTO> findAllDto() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public Optional<UserWithRolesDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public UserWithRolesDTO createDto(UserWithRolesDTO d) {
        return mapper.toDto(repo.save(mapper.toEntity(d)));
    }

    @Transactional
    public UserWithRolesDTO updateDto(Integer id, UserWithRolesDTO d) {
        User e = mapper.toEntity(d);
        e.setId(id);
        return mapper.toDto(repo.save(e));
    }
}
