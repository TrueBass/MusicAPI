package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.refresh_token_dtos.ResponseTokenDto;
import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;
import com.example.musicapi.entities.Role;
import com.example.musicapi.entities.User;
import com.example.musicapi.exceptions.InvalidPasswordException;
import com.example.musicapi.exceptions.NotFoundException;
import com.example.musicapi.exceptions.UnauthorizedException;
import com.example.musicapi.repositories.IRefreshTokenRepository;
import com.example.musicapi.exceptions.AlreadyExistsException;
import com.example.musicapi.repositories.IRoleRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final IRefreshTokenService refreshTokenService;
    private final IRefreshTokenRepository refreshTokenRepository;

    private CustomUserDetailsService customUserDetailsService;
    private AuthenticationManager authenticationManager;

    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Override
    public ResponseTokenDto registerUser(UserAuthDto userAuthDto) {
        Optional<User> userFoundByEmail = userRepository.findByEmail(userAuthDto.getEmail());
        if(userFoundByEmail.isPresent()) {
            throw new AlreadyExistsException(
                    String.format("User with email %s already exists", userAuthDto.getEmail())
            );
        }

        Optional<User> userFoundByUsername = userRepository.findByUsername(userAuthDto.getUsername());
        if(userFoundByUsername.isPresent()) {
            throw new AlreadyExistsException(
                    String.format("User with username %s already exists", userAuthDto.getUsername())
            );
        }

        User newUser = Mapper.MapToUser(userAuthDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Role defaultRole = roleRepository.findByName("USER").orElseThrow(
                () -> new RuntimeException("Default role 'USER' not found")
        );
        newUser.getRoles().add(defaultRole);

        User savedUser = userRepository.save(newUser);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(savedUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateToken(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(savedUser).getToken();

        return new ResponseTokenDto(accessToken, "Bearer", refreshToken);
    }

    @Override
    public ResponseTokenDto loginUser(UserLoginDto userLoginDto) {
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
        return authenticateUser(user.get(), auth);
    }

    @Override
    public ResponseTokenDto refreshUser(String refreshToken) {
        var existingToken = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));

        if (existingToken.getExpiresOn().before(new Date())) {
            refreshTokenRepository.delete(existingToken);
            throw new UnauthorizedException("Refresh token expired");
        }

        var user = existingToken.getUser();
        var userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        return authenticateUser(user, authentication);
    }

    @Override
    public void logoutUser(String refreshToken) {
        var existingToken = refreshTokenService.findByToken(refreshToken);
        if (existingToken.isEmpty()) {
            throw new NotFoundException("Refresh token not found");
        }

        refreshTokenRepository.delete(existingToken.get());
    }

    private ResponseTokenDto authenticateUser(User user, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtProvider.generateToken(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        return new ResponseTokenDto(accessToken, "Bearer", refreshToken);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        User foundUser = user.isPresent() ? user.get():
                user.orElseThrow(
                    () -> new NotFoundException("User not found")
                );

        return Mapper.MapToUserDto(foundUser);
    }
}
