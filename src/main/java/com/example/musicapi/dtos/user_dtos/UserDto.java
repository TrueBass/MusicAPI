package com.example.musicapi.dtos.user_dtos;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Username can't be empty.")
    @Size(min = 6, max = 30, message = "Username length must have from 6 to 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, digits and underscores")
    private String username;

    @NotBlank(message = "Email can't be empty.")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format.")
    private String email;

    @Min(value = 0, message = "Social credit must be positive integer")
    private int socialCredit;

    private boolean isAdmin;
    private boolean isBanned;
}
