package com.example.musicapi.dtos.user_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUsernameDto(
        @NotBlank(message = "Username can't be empty.")
        @Size(min = 6, max = 30, message = "Username must have from 6 to 30 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, digits and underscores")
        String username) {}
