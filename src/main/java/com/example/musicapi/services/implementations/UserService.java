package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;
import com.example.musicapi.repositories.IUserRepository;
import com.example.musicapi.services.definitions.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto loginUser(UserLoginDto userDto) {
        return null;
    }
}
