package com.example.Intern.Repository;

import com.example.Intern.Entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.token = :token")
    Token findTokenByToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.token = :token")
    void deleteTokenWithUser(@Param("token") String token);
}