package com.example.musicapi.dtos.playlist_dtos;

import java.util.Date;

public record PlaylistDto(
        Long id,
        String title,
        Date createdAt,
        Date modifiedAt,
        boolean isPrivate,
        Long usedId) {
}
