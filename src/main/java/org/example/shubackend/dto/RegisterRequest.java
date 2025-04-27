package org.example.shubackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @Email String email,
        String role   // optional: admin / manager / maintainer / inspector
) {
}