package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.genre_dtos.GenreDto;
import com.example.musicapi.entities.Genre;
import com.example.musicapi.exceptions.AlreadyExistsException;
import com.example.musicapi.exceptions.NotFoundException;
import com.example.musicapi.repositories.IGenreRepository;
import com.example.musicapi.services.definitions.IGenreService;
import com.example.musicapi.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService implements IGenreService {
  private final IGenreRepository genreRepository;

  @Override
  public List<GenreDto> getAllGenres() {
    return genreRepository.getAllGenres();
  }

  @Override
  public GenreDto getGenreById(Long genreId) {
    Genre genre = genreRepository.findById(genreId).orElseThrow(
            () -> new NotFoundException("Genre is not found with id " + genreId)
    );
    return Mapper.MapToGenreDto(genre);
  }

  @Override
  public GenreDto createGenre(String genreName) {
    if (genreRepository.findByName(genreName).isPresent()) {
      throw new AlreadyExistsException("Genre with name '" + genreName + "' already exists.");
    }

    Genre newGenre = genreRepository.save(
            Genre.builder()
            .name(genreName)
            .build()
    );

    return Mapper.MapToGenreDto(newGenre);
  }

}
