package com.example.musicapi.dtos.user_dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
    private String username;
    private String email;
    private String password;
}