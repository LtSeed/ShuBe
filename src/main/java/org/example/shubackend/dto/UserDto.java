package org.example.shubackend.dto;

import org.example.shubackend.entity.Role;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private final Set<Role> roles = new HashSet<>();
    private Integer id;
    private String username;
    private String account;
    private Instant createdAt;
    private String email;
    private String phone;
}