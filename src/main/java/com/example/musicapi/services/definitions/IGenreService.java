package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.genre_dtos.GenreDto;

import java.util.List;

public interface IGenreService {
  List<GenreDto> getAllGenres();
  GenreDto getGenreById(Long genreId);
  GenreDto createGenre(String genreName);
}
