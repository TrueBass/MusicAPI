package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;
import com.example.musicapi.entities.User;
import com.example.musicapi.exceptions.AlreadyExistsException;
import com.example.musicapi.repositories.IUserRepository;
import com.example.musicapi.services.definitions.IUserService;
import com.example.musicapi.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Override
    public UserDto registerUser(UserAuthDto userAuthDto) {
        Optional<User> userFoundByEmail = userRepository.findByEmail(userAuthDto.getEmail());
        if(userFoundByEmail.isPresent()) {
            throw new AlreadyExistsException(
                    String.format("User with email %s already exists", userAuthDto.getEmail())
            );
        }

        Optional<User> userFoundByUsername = userRepository.findByUsername(userAuthDto.getUsername());
        if(userFoundByUsername.isPresent()) {
            throw new AlreadyExistsException(
                    String.format(
                            "User with username %s already exists",
                            userAuthDto.getUsername()
                    )
            );
        }

        User newUser = Mapper.MapToUser(userAuthDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        User savedUser = userRepository.save(newUser);

        return Mapper.MapToUserDto(savedUser);
    }

    @Override
    public UserDto loginUser(UserLoginDto userDto) {
        return null;
    }
}
