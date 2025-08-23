package com.example.musicapi.dtos.song_dtos;


import java.util.Date;

public record SongDto(
        Long id,
        String title,
        String author,
        Date addedAt,
        int likes,
        byte[] data,
        Long duration,
        Long genreId) {
}
