package com.example.musicapi.dtos.user_dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {

    @NotBlank(message = "Username can't be empty.")
    @Size(min = 6, max = 30, message = "Username length must have from 6 to 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, digits and underscores")
    private String username;

    @NotBlank(message = "Email can't be empty.")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password can't be empty.")
    @Size(min = 8, message = "Password must have at least 8 characters.")
    private String password;
}