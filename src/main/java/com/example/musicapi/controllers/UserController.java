package com.example.musicapi.controllers;

import com.example.musicapi.dtos.refresh_token_dtos.RefreshTokenDto;
import com.example.musicapi.dtos.user_dtos.*;
import com.example.musicapi.dtos.refresh_token_dtos.ResponseTokenDto;
import com.example.musicapi.services.implementations.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/users")
public class UserController {
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDto> login(@RequestBody @Valid UserLoginDto loginDto) {
        var token = userService.loginUser(loginDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<ResponseTokenDto> signup(@RequestBody @Valid UserAuthDto userDto) {
        var token = userService.registerUser(userDto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseTokenDto> refresh(@RequestBody @Valid RefreshTokenDto dto) {
        var token = userService.refreshUser(dto.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshTokenDto dto) {
        userService.logoutUser(dto.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordDto updatePasswordDto) {
        userService.updatePassword(updatePasswordDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-email")
    public ResponseEntity<Void> updateEmail(@RequestBody @Valid UpdateEmailDto updateEmailDto) {
        userService.updateEmail(updateEmailDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-username")
    public ResponseEntity<Void> updateUsername(@RequestBody @Valid UpdateUsernameDto updateUsernameDto) {
        userService.updateUsername(updateUsernameDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

}
