package org.example.shubackend.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.dto.*;
import org.example.shubackend.service.AuthService;
import org.example.shubackend.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService svc;
    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest req) {
        log.info("Login request: " + req);
        return svc.login(req);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestHeader("Authorization") String bearer) {
        String token = bearer.replace("Bearer ", "");
        return svc.refresh(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String bearer) {
        svc.logout(bearer.replace("Bearer ", ""));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest req) {
        return svc.register(req);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        // 验证两次密码是否一致
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("两次输入的密码不一致");
        }

        passwordResetService.resetPassword(
                request.username(),
                request.phone(),
                request.email(),
                request.newPassword()
        );
        return ResponseEntity.ok().build();
    }
}
