package com.example.musicapi.dtos.song_dtos;

import com.example.musicapi.enums.MusicGenre;

import java.util.Date;

public record SongDto(
        Long id,
        String title,
        String author,
        Date addedAt,
        Long likes,
        byte[] data,
        Long duration,
        MusicGenre genre) {
}
