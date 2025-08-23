package com.example.musicapi.dtos.genre_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGenreDto {
  @NotBlank(message = "Genre name cannot be blank")
  @Size(min = 2, max = 50, message = "Genre name must be between 2 and 50 characters")
  private String name;
}
