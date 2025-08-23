package com.example.musicapi.repositories;

import com.example.musicapi.dtos.genre_dtos.GenreDto;
import com.example.musicapi.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IGenreRepository  extends JpaRepository<Genre, Long> {
  Optional<Genre> findByName(String name);

  @Query("""
    SELECT new com.example.musicapi.dtos.genre_dtos.GenreDto(
      g.id, g.name
    ) FROM Genre g
  """)
  List<GenreDto> getAllGenres();
}
