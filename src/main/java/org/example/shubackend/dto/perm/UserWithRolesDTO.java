package org.example.shubackend.dto.perm;

import java.time.Instant;
import java.util.List;

public record UserWithRolesDTO(
        Integer id,
        String username,
        String account,
        Instant createdAt,
        String email,
        String phone,
        List<RoleDTO> roles
) {
}