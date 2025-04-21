package com.example.musicapi.controllers;

import com.example.musicapi.dtos.refresh_token_dtos.RefreshTokenDto;
import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.refresh_token_dtos.ResponseTokenDto;
import com.example.musicapi.dtos.user_dtos.UserLoginDto;
import com.example.musicapi.services.implementations.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDto> login(@RequestBody UserLoginDto loginDto) {
        var token = userService.loginUser(loginDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<ResponseTokenDto> signup(@RequestBody UserAuthDto userDto) {
        var token = userService.registerUser(userDto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseTokenDto> refresh(@RequestBody RefreshTokenDto dto) {
        var token = userService.refreshUser(dto.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenDto dto) {
        userService.logoutUser(dto.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}
