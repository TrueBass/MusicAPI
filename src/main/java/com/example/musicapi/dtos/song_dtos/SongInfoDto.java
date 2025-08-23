package com.example.musicapi.dtos.song_dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record SongInfoDto(
        Long id,
        String title,
        String author,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(pattern = "dd-MM-yyyy")
        Date addedAt,
        int likes,
        Long duration,
        Long genreId
) {

}
