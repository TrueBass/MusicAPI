package com.example.musicapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RefreshTokenDto {
    private String token;
    private Date expiresOn;
    private Date createdAt;
}
