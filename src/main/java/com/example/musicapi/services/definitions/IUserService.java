package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;

public interface IUserService {
    UserDto registerUser(UserDto userDto);

    UserDto loginUser(UserLoginDto userDto);
}
