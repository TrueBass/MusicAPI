package com.example.musicapi.dtos.user_dtos;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private int socialCredit;
    private boolean isAdmin;
    private boolean isBanned;
}
