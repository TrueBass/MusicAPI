package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.refresh_token_dtos.ResponseTokenDto;
import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;

public interface IUserService {
    RefreshTokenDto registerUser(UserAuthDto userDto);

    ResponseTokenDto loginUser(UserLoginDto userDto);

    ResponseTokenDto refreshUser(String refreshToken);

    void logoutUser(String refreshToken);
}
