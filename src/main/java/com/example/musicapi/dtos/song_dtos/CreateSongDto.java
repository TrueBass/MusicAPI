package com.example.musicapi.dtos.song_dtos;

public record CreateSongDto(
        String title,
        String author,
        Long duration,
        byte[] data,
        Long playlistId) {
}
