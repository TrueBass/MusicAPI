package com.example.musicapi.controllers;

import com.example.musicapi.dtos.genre_dtos.GenreDto;
import com.example.musicapi.services.definitions.IGenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("music-api/genres")
@AllArgsConstructor
public class GenreController {
  private final IGenreService genreService;

  @GetMapping("/all")
  public ResponseEntity<List<GenreDto>> getAllGenre() {
    List<GenreDto> genres = genreService.getAllGenres();
    return ResponseEntity.ok(genres);
  }

  @GetMapping
  public ResponseEntity<GenreDto> getGenre(@RequestParam Long genreId) {
    GenreDto genreDto = genreService.getGenreById(genreId);
    return ResponseEntity.ok(genreDto);
  }

  @PostMapping
  public ResponseEntity<GenreDto> addGenre(@RequestBody String name) {
    GenreDto genreDto = genreService.createGenre(name);
    return new ResponseEntity<>(genreDto, HttpStatus.CREATED);
  }
}
