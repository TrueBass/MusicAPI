package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.refresh_token_dtos.ResponseTokenDto;
import com.example.musicapi.dtos.user_dtos.*;
import com.example.musicapi.entities.Role;
import com.example.musicapi.entities.User;
import com.example.musicapi.enums.UserRoles;
import com.example.musicapi.exceptions.*;
import com.example.musicapi.repositories.IRefreshTokenRepository;
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

        Role defaultRole = roleRepository.findByName(UserRoles.USER.name()).orElseThrow(
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

    @Override
    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        if (updatePasswordDto.oldPassword().equals(updatePasswordDto.newPassword())) {
            throw new InvalidPasswordException("New password cannot be equal to the old one.");
        }
        if (!updatePasswordDto.newPassword().equals(updatePasswordDto.confirmPassword())) {
            throw new InvalidPasswordException("Confirm password and new password do not match.");
        }

        var user = getCurrentUser();

        if (!passwordEncoder.matches(updatePasswordDto.oldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDto.newPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateEmail(UpdateEmailDto updateEmailDto) {
        var user = getCurrentUser();

        if (userRepository.findByEmail(updateEmailDto.email()).isPresent()) {
            throw new EmailAlreadyTakenException("Provided email is already taken by another user.");
        }

        user.setEmail(updateEmailDto.email());
        userRepository.save(user);
    }

    @Override
    public void updateUsername(UpdateUsernameDto updateUsernameDto) {
        var user = getCurrentUser();

        if (userRepository.findByUsername(updateUsernameDto.username()).isPresent()) {
            throw new UsernameAlreadyTakenException("Provided username is already taken by another user.");
        }

        user.setUsername(updateUsernameDto.username());
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }

        String username = auth.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long UserId) {
        var user = getCurrentUser();
        if (!user.getId().equals(UserId)) {
            throw new UnauthorizedException("Only user can delete their account.");
        }
        userRepository.delete(user);
    }

    @Override
    public int updateSocialCredit(Long userId, int newCredit) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        int changedSocialCredit = user.getSocialCredit() + newCredit;

        user.setSocialCredit(changedSocialCredit);
        userRepository.save(user);
        return changedSocialCredit;
    }
}
