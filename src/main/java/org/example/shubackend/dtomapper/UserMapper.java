package org.example.shubackend.dtomapper;

import org.example.shubackend.dto.perm.UserWithRolesDTO;
import org.example.shubackend.entity.User;
import org.example.shubackend.repository.RoleRepository;
import org.example.shubackend.dtomapper.perm.RoleMapper;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public abstract class UserMapper {

    @Autowired
    protected RoleRepository roleRepo;
    @Autowired
    protected RoleMapper roleMapper;

    public UserWithRolesDTO toDto(User u) {
        return new UserWithRolesDTO(
                u.getId(), u.getUsername(), u.getAccount(),
                u.getCreatedAt(), u.getEmail(), u.getPhone(),
                u.getRoles().stream().map(roleMapper::toDto).toList()
        );
    }

    public User toEntity(UserWithRolesDTO dto) {
        User u = new User();
        u.setId(dto.id());
        u.setUsername(dto.username());
        u.setAccount(dto.account());
        u.setCreatedAt(dto.createdAt());
        u.setEmail(dto.email());
        u.setPhone(dto.phone());
        if (dto.roles() != null) {
            u.setRoles(dto.roles().stream()
                    .map(r -> roleRepo.getReferenceById(r.id()))
                    .collect(Collectors.toSet()));
        }
        return u;
    }
}
