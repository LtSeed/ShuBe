package org.example.shubackend.service.crud;

import org.example.shubackend.entity.Role;
import org.example.shubackend.entity.User;
import org.example.shubackend.entity.UserRole;
import org.example.shubackend.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserRoleCrudService extends GenericCrudService<UserRole, Integer> {
    private final UserCrudService userCrudService;
    private final RoleCrudService roleCrudService;

    public UserRoleCrudService(UserRoleRepository repo, UserCrudService userCrudService, RoleCrudService roleCrudService) {
        super(repo);
        this.userCrudService = userCrudService;
        this.roleCrudService = roleCrudService;
    }

    public void assignRoleToUser(int userId, int roleId) {
        User user = userCrudService.find(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        Role role = roleCrudService.find(roleId).orElseThrow(() -> new NoSuchElementException("Role with id " + roleId + " not found"));
        UserRole build = UserRole.builder().user(user).role(role).build();
        repo.save(build);
    }

    public void removeRoleFromUser(int userId, int roleId) {
        List<UserRole> list = repo.findAll().stream().filter(u -> u.getUser().getId().equals(userId))
                .filter(u -> u.getRole().getId().equals(roleId)).toList();
        repo.deleteAll(list);
    }
}