package org.example.shubackend.web.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.perm.UserWithRolesDTO;
import org.example.shubackend.repository.RoleRepository;
import org.example.shubackend.service.crud.UserCrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/* UserController (全 CRUD + 角色赋予/移除) */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCrudService userSvc;
    private final RoleRepository roleRepo;

    /* 列表 / 单查 */
    @PreAuthorize("hasPermission(null,'USERS_READ')")
    @GetMapping
    public List<UserWithRolesDTO> list() {
        return userSvc.findAllDto();
    }

    @PreAuthorize("hasPermission(null,'USERS_READ')")
    @GetMapping("/{id}")
    public Optional<UserWithRolesDTO> one(@PathVariable Integer id) {
        return userSvc.findDto(id);
    }

    /* 创建 / 更新 / 删除 */
    @PreAuthorize("hasPermission(null,'USERS_CREATE')")
    @PostMapping
    public UserWithRolesDTO create(@RequestBody UserWithRolesDTO d) {
        return userSvc.createDto(d);
    }

    @PreAuthorize("hasPermission(null,'USERS_UPDATE')")
    @PutMapping("/{id}")
    public UserWithRolesDTO update(@PathVariable Integer id, @RequestBody UserWithRolesDTO d) {
        return userSvc.updateDto(id, d);
    }

    @PreAuthorize("hasPermission(null,'USERS_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userSvc.delete(id);
    }

}
