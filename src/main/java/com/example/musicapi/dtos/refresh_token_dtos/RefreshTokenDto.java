package com.example.musicapi.dtos.refresh_token_dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenDto {
    private String accessToken;
    private String accessTokenType = "Bearer";
    private String refreshToken;
}
