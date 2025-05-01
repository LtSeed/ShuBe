package org.example.shubackend.dtomapper.perm;

import org.example.shubackend.dto.perm.PermissionDTO;
import org.example.shubackend.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDTO toDto(Permission e);
}
