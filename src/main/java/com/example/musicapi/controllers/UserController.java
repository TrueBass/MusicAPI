package com.example.musicapi.controllers;

import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.user_dtos.UserDto;
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

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserAuthDto userDto) {
        var user = userService.registerUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
