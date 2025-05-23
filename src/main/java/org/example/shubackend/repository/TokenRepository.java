package org.example.shubackend.repository;

import org.example.shubackend.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByTokenValueAndStatus(String tokenValue, String status);
}
