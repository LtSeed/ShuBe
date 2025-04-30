package org.example.shubackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.Token;
import org.example.shubackend.entity.User;
import org.example.shubackend.repository.TokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().startsWith("/auth")) {
            log.info("Auth request: " + request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String tokenValue = header.substring(7);
        Token token = tokenRepository.findByTokenValueAndStatus(tokenValue, "active")
                .filter(t -> t.getExpiresAt() == null || t.getExpiresAt().isAfter(Instant.now()))
                .orElse(null);
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = token.getUser();
            var authorities = user.getRoles().stream()
                    .flatMap(r -> r.getPermissions().stream())
                    .map(p -> new SimpleGrantedAuthority(p.getName().name()))
                    .collect(Collectors.<GrantedAuthority>toSet());
            // 也可添加 ROLE_ 前缀
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
