package com.example.musicapi.repositories;

import com.example.musicapi.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitle(String title);
}
