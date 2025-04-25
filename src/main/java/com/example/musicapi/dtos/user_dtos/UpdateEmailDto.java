package com.example.musicapi.dtos.user_dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailDto(
        @NotBlank(message = "Email can't be empty.")
        @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format.")
        String email) {}
