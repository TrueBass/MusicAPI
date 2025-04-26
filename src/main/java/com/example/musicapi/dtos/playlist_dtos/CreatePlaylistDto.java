package com.example.musicapi.dtos.playlist_dtos;

public record CreatePlaylistDto(Long userId, String title, boolean isPrivate) {
}
