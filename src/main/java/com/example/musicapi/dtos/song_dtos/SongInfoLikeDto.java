package com.example.musicapi.dtos.song_dtos;

import com.example.musicapi.enums.MusicGenre;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class SongInfoLikeDto {
  Long id;
  String title;
  String author;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = "dd-MM-yyyy")
  Date addedAt;
  int likes;
  Long duration;
  MusicGenre genre;
  boolean isLikedByUser;
  String uploader;
}
