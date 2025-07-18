package com.example.musicapi.repositories;

import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPlayListRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUserId(Long userId);
    @Query("""
        SELECT p 
        FROM Playlist p 
        LEFT JOIN p.songs s 
        WHERE p.user.id = :userId 
        GROUP BY p.id 
        ORDER BY COUNT(s) DESC 
        LIMIT 1
    """)
    Optional<Playlist> findLargestPlaylist(@Param("userId") Long userId);
}
