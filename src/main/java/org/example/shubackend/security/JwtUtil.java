package org.example.shubackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey key;

    public JwtUtil(JwtProperties props) {
        String secret = props.getSecret();
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String username, Map<String, Object> extra, long minutes) {
        Instant now = Instant.now();
        return Jwts.builder().subject(username).claims(extra)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(minutes * 60)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

    public String username(String token) {
        return parse(token).getPayload().getSubject();
    }

    public boolean isExpired(String token) {
        return parse(token).getPayload().getExpiration().before(new Date());
    }
}
