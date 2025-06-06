package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.song_dtos.SongInfoDto;

import java.util.List;

public interface ISongService {
    SongDto addSong(CreateSongDto songDto);

    List<String> findByTitle(String title);

    List<SongInfoDto> getAllSongsInfo(Long playlistId);

    byte[] getSongBytes(Long songId);
}
