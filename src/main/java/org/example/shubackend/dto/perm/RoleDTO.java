package org.example.shubackend.dto.perm;

import java.util.List;

public record RoleDTO(Integer id, String name, List<PermissionDTO> permissions) {
}
