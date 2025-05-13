package com.example.musicapi.repositories;

import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitle(String title);

    @Query("SELECT new com.example.musicapi.dtos.song_dtos.SongInfoDto(" +
            "s.id, " +
            "s.title, " +
            "s.author, " +
            "s.addedAt, " +
            "s.likes, " +
            "s.duration, " +
            "s.genre, " +
            "s.playlist.id) " +
            "FROM Song s " +
            "WHERE s.playlist.id = :playlistId")
    List<SongInfoDto> getAllWithoutData(@Param("playlistId") Long playlistId);

    @Query("SELECT s.data FROM Song s WHERE s.id = :id")
    byte[] getDataById(Long id);
}
