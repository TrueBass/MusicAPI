package com.example.musicapi.dtos.user_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(
        @NotBlank(message = "Password can't be empty.")
        @Size(min = 8, message = "Password must have at least 8 characters.")
        String oldPassword,

        @NotBlank(message = "Password can't be empty.")
        @Size(min = 8, message = "Password must have at least 8 characters.")
        String newPassword,

        @NotBlank(message = "Password can't be empty.")
        @Size(min = 8, message = "Password must have at least 8 characters.")
        String confirmPassword) {}
