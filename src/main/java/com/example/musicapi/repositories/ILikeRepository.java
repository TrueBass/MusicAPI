package com.example.musicapi.repositories;

import com.example.musicapi.entities.Like;
import com.example.musicapi.entities.Song;
import com.example.musicapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ILikeRepository extends JpaRepository<Like, Long> {
  Optional<Like> findByUserAndSong(User user, Song song);
  long countBySong(Song song);
}
