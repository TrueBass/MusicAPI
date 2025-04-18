package com.example.musicapi.services.implementations;

import com.example.musicapi.entities.RefreshToken;
import com.example.musicapi.entities.User;
import com.example.musicapi.repositories.IRefreshTokenRepository;
import com.example.musicapi.services.definitions.IRefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService implements IRefreshTokenService {

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private int refreshTokenExpirationMs;

    private final IRefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(IRefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + refreshTokenExpirationMs);

        refreshToken.setUser(user);
        refreshToken.setCreatedAt(currentDate);
        refreshToken.setExpiresOn(expirationDate);
        refreshToken.setToken(UUID.randomUUID().toString());

        var oldRefreshToken = refreshTokenRepository.findByUser(user);
        oldRefreshToken.ifPresent(token -> refreshTokenRepository.delete(token));

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }


    //TODO Add refresh token validation
}
