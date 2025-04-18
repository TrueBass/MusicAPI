package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.refresh_token_dtos.RefreshTokenDto;
import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;

public interface IUserService {
    UserDto registerUser(UserAuthDto userDto);

    RefreshTokenDto loginUser(UserLoginDto userDto);
}
