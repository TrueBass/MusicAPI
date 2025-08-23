package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.dtos.song_dtos.SongInfoLikeDto;
import com.example.musicapi.entities.Song;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ISongService {
    SongDto addSong(CreateSongDto songDto);

    @Transactional
    void deleteSong(Long songId);

    List<String> findByTitle(String title);

    List<SongInfoLikeDto> findByQuery(String query, Long userId);

    List<SongInfoDto> getAllSongsInfo(Long playlistId);

    List<SongInfoLikeDto> getAllPopularSongs(Long userId);
    List<SongInfoLikeDto> getAllPopularSongsPage(Long userId, Long cursor, int limit);

    byte[] getSongBytes(Long songId);

    List<SongInfoDto> getTopFiveSongs(Long userId);
}
