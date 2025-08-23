package com.example.musicapi.dtos.playlist_dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record PlaylistDto(
        Long id,
        String title,
        @JsonFormat(pattern = "dd-MM-yyyy")
        Date createdAt,
        @JsonFormat(pattern = "dd-MM-yyyy")
        Date modifiedAt,
        boolean isPrivate,
        Long userId) {
}
