package com.example.musicapi.repositories;

import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlayListRepository extends JpaRepository<Playlist, Long> {
}
