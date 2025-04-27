package org.example.shubackend.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.AuthRequest;
import org.example.shubackend.dto.AuthResponse;
import org.example.shubackend.dto.RegisterRequest;
import org.example.shubackend.dto.RegisterResponse;
import org.example.shubackend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService svc;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest req){
        return svc.login(req);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestHeader("Authorization") String bearer){
        String token = bearer.replace("Bearer ","");
        return svc.refresh(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String bearer){
        svc.logout(bearer.replace("Bearer ",""));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest req){
        return svc.register(req);
    }
}
