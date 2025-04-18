package com.example.musicapi.dtos.user_dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "This field can't be empty")
    @Pattern(regexp = "^([a-zA-Z0-9_]{6,30})|([\\w-]+@([\\w-]+\\.)+[\\w-]{2,4})$",
            message = "Invalid email or username format. Username must have from 6 to 30 characters and contain only letters, digits and underscores, or enter valid email address.")
    private String emailOrUsername;

    @NotBlank(message = "Password can't be empty.")
    @Size(min = 8, message = "Password must have at least 8 characters.")
    private String password;
}
