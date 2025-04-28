package org.example.shubackend.web.crud;

import org.example.shubackend.service.crud.UserRoleCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-roles")
public class UserRoleController {

    @Autowired
    private UserRoleCrudService userRoleService;

    @PreAuthorize("hasPermission(null,'USER_ROLE_ASSIGN')")
    @PostMapping
    public ResponseEntity<Void> assignRoleToUser(@RequestParam Integer userId, @RequestParam Integer roleId) {
        userRoleService.assignRoleToUser(userId, roleId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasPermission(null,'USER_ROLE_REMOVE')")
    @DeleteMapping
    public ResponseEntity<Void> removeRoleFromUser(@RequestParam Integer userId, @RequestParam Integer roleId) {
        userRoleService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.noContent().build();
    }
}
