package com.example.musicapi.services.definitions;

import com.example.musicapi.entities.RefreshToken;
import com.example.musicapi.entities.User;

import java.util.Optional;

public interface IRefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(User user);
}
