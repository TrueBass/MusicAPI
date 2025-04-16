package com.example.musicapi.dtos.song_dtos;

import com.example.musicapi.enums.MusicGenre;

public record CreateSongDto(
        String title,
        String author,
        Long duration,
        byte[] data,
        MusicGenre genre,
        Long playlistId) {
}
