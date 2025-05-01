package org.example.shubackend.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static final boolean DEBUG = true;

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        log.info("check permission: {} of {}", auth, permission);
        if (auth == null || !(permission instanceof String perm)) return false;
        if (DEBUG && auth.getPrincipal().equals("anonymousUser")) return true;
        User principal = (User) auth.getPrincipal();
        log.info("user: {}", principal.getUsername());
        return principal.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(r -> r.getName().name().equalsIgnoreCase(perm));
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        return hasPermission(auth, null, permission);
    }
}