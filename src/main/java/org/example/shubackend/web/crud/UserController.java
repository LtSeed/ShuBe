package org.example.shubackend.web.crud;

import org.example.shubackend.entity.User;
import org.example.shubackend.service.crud.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserCrudService userService;


    @PreAuthorize("hasPermission(null,'USER_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.find(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasPermission(null,'USER_READ')")
    @GetMapping
    public ResponseEntity<List<User>> getUserById() {
        List<User> user = userService.findAll();
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasPermission(null,'USER_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
