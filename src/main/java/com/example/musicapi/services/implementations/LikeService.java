package com.example.musicapi.services.implementations;

import com.example.musicapi.entities.Like;
import com.example.musicapi.entities.Song;
import com.example.musicapi.entities.User;
import com.example.musicapi.exceptions.NotFoundException;
import com.example.musicapi.repositories.ILikeRepository;
import com.example.musicapi.repositories.ISongRepository;
import com.example.musicapi.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LikeService {
  private final ILikeRepository likeRepository;
  private final IUserRepository userRepository;
  private final ISongRepository songRepository;

  public void likeSong(Long userId, Long songId) {
    User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException("User not found"));
    Song song = songRepository.findById(songId).orElseThrow(()->new NotFoundException("Song not found"));

    // Prevent duplicate likes
    if (likeRepository.findByUserAndSong(user, song).isEmpty()) {
      Like like = Like.builder().user(user).song(song).likedAt(new Date()).build();
      likeRepository.save(like);
    }
  }

  @Transactional
  public void unlikeSong(Long userId, Long songId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));
    Song song = songRepository.findById(songId)
            .orElseThrow(() -> new NotFoundException("Song not found"));

    likeRepository.findByUserAndSong(user, song)
            .ifPresent(likeRepository::delete);
  }

  public long getLikeCount(Long songId) {
    Song song = songRepository.findById(songId).orElseThrow();
    return likeRepository.countBySong(song);
  }

  public boolean hasUserLiked(Long userId, Long songId) {
    User user = userRepository.findById(userId).orElseThrow();
    Song song = songRepository.findById(songId).orElseThrow();
    return likeRepository.findByUserAndSong(user, song).isPresent();
  }
}
