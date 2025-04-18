package com.example.musicapi.repositories;

import com.example.musicapi.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
