package com.example.musicapi.repositories;

import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPlayListRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUserId(Long userId);
//    Playlist deleteByPlaylistId(Long playlistId);
}
