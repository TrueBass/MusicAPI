package com.example.musicapi.services.definitions;

import org.springframework.transaction.annotation.Transactional;

public interface ILikeService {
  void likeSong(Long userId, Long songId);
  @Transactional
  void unlikeSong(Long userId, Long songId);
  long getLikeCount(Long songId);
  boolean hasUserLiked(Long userId, Long songId);
}
