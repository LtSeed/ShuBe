package org.example.shubackend.dtomapper.perm;

import org.example.shubackend.dto.perm.RoleDTO;
import org.example.shubackend.entity.Role;
import org.example.shubackend.repository.PermissionRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public abstract class RoleMapper {

    @Autowired
    protected PermissionMapper permMapper;
    @Autowired
    protected PermissionRepository permRepo;

    public RoleDTO toDto(Role r) {
        return new RoleDTO(
                r.getId(),
                r.getName(),
                r.getPermissions().stream().map(permMapper::toDto).toList()
        );
    }

    /**
     * toEntity 仅映射 id / 名称 / 权限集合(id 列表)
     */
    public Role toEntity(RoleDTO dto) {
        Role r = new Role();
        r.setId(dto.id());
        r.setName(dto.name());
        if (dto.permissions() != null) {
            r.setPermissions(
                    dto.permissions().stream()
                            .map(p -> permRepo.getReferenceById(p.id()))
                            .collect(Collectors.toSet())
            );
        }
        return r;
    }
}
