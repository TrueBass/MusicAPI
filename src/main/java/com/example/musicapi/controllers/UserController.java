package com.example.musicapi.controllers;

import com.example.musicapi.services.implementations.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/users")
public class UserController {
    private final UserService userService;

}
