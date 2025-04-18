package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.refresh_token_dtos.RefreshTokenDto;
import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;
import com.example.musicapi.entities.User;
import com.example.musicapi.exceptions.InvalidPasswordException;
import com.example.musicapi.exceptions.NotFoundException;
import com.example.musicapi.repositories.IRefreshTokenRepository;
import com.example.musicapi.exceptions.AlreadyExistsException;
import com.example.musicapi.repositories.IUserRepository;
import com.example.musicapi.services.definitions.IRefreshTokenService;
import com.example.musicapi.services.definitions.IUserService;
import com.example.musicapi.utils.JwtProvider;
import com.example.musicapi.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserRepository userRepository;
    private JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final IRefreshTokenService refreshTokenService;

    private CustomUserDetailsService customUserDetailsService;
    private AuthenticationManager authenticationManager;

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
    public RefreshTokenDto loginUser(UserLoginDto userLoginDto) {
        var usernameOrEmail = userLoginDto.getEmailOrUsername();

        Optional<User> user;
        if (EMAIL_PATTERN.matcher(usernameOrEmail).matches()) {
            user = userRepository.findByEmail(usernameOrEmail);
        } else {
            user = userRepository.findByUsername(usernameOrEmail);
        }

        if (user.isEmpty()) {
            throw new NotFoundException("User with the specified email/username is not found");
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.get().getPassword())) {
            throw new InvalidPasswordException("Invalid password provided");
        }

        var authToken = new UsernamePasswordAuthenticationToken(user.get().getUsername(), userLoginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtProvider.generateToken(auth);
        String refreshToken = refreshTokenService.createRefreshToken(user.get()).getToken();

        return new RefreshTokenDto(accessToken, "Bearer", refreshToken);
    }
}
