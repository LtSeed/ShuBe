package org.example.shubackend.service;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.AuthRequest;
import org.example.shubackend.dto.AuthResponse;
import org.example.shubackend.dto.RegisterRequest;
import org.example.shubackend.dto.RegisterResponse;
import org.example.shubackend.entity.Token;
import org.example.shubackend.entity.User;
import org.example.shubackend.repository.TokenRepository;
import org.example.shubackend.repository.UserRepository;
import org.example.shubackend.security.JwtAuthFilter;
import org.example.shubackend.security.JwtProperties;
import org.example.shubackend.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authMgr;
    private final JwtUtil jwt;
    private final JwtProperties props;
    private final TokenRepository tokenRepo;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final JwtAuthFilter jwtAuthFilter;

    public AuthResponse login(AuthRequest req) {
        authMgr.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));

        // generate tokens
        String access = jwt.generate(req.username(), Map.of("type", "access"), props.getAccessMinutes());
        String refresh = jwt.generate(req.username(), Map.of("type", "refresh"), props.getRefreshDays() * 24 * 60);

        // persist refresh
        User user = userRepository.findByUsername(req.username()).orElseThrow(() -> new RuntimeException("user not found"));
        jwtAuthFilter.getAccessTokens().put(access, user);

        tokenRepo.save(Token.builder()
                .user(user)
                .tokenValue(refresh)
                .tokenType("refresh")
                .status("active")
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(props.getRefreshDays() * 24 * 3600))
                .build());

        return new AuthResponse(access, refresh);
    }

    public AuthResponse refresh(String refreshToken) {
        var jws = jwt.parse(refreshToken);
        if (jwt.isExpired(refreshToken)) throw new RuntimeException("expired");

        var dbTok = tokenRepo.findByTokenValueAndStatus(refreshToken, "active")
                .orElseThrow(() -> new RuntimeException("revoked"));
        String username = jws.getPayload().getSubject();

        String newAccess = jwt.generate(username, Map.of("type", "access"), props.getAccessMinutes());
        return new AuthResponse(newAccess, refreshToken);
    }

    public void logout(String refreshToken) {
        tokenRepo.findByTokenValueAndStatus(refreshToken, "active")
                .ifPresent(t -> {
                    t.setStatus("revoked");
                    tokenRepo.save(t);
                });
    }

    /* ---------- REGISTER ---------- */
    @Transactional
    public RegisterResponse register(RegisterRequest req) {

        if (userRepository.findByUsername(req.username()).isPresent())
            throw new RuntimeException("用户名已存在");

        org.example.shubackend.entity.User u = org.example.shubackend.entity.User.builder()
                .username(req.username())
                .account(req.username())
                .password(encoder.encode(req.password()))
                .createdAt(Instant.now())
                .email(req.email())
                .phone(req.phone())
                .roles(new HashSet<>())
                .build();

        userRepository.save(u);
        return new RegisterResponse(u.getId(), u.getUsername());
    }
}

