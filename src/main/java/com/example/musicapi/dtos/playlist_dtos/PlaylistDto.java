package com.example.musicapi.dtos.playlist_dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

public record PlaylistDto(
        Long id,
        String title,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(pattern = "dd-MM-yyyy")
        Date createdAt,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(pattern = "dd-MM-yyyy")
        Date modifiedAt,
        boolean isPrivate,
        Long userId) {
}
