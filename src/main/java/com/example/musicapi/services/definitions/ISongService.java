package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.entities.Song;

import java.util.List;

public interface ISongService {
    SongDto addSong(CreateSongDto songDto);

    List<String> findByTitle(String title);

    List<SongInfoDto> getAllSongsInfo(Long playlistId);

    List<SongInfoDto> getAllPopularSongs();

    byte[] getSongBytes(Long songId);

    List<SongInfoDto> getTop10Songs(Long userId);
}
