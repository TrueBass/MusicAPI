package com.example.musicapi.controllers;

import com.example.musicapi.dtos.like_dtos.LikeDto;
import com.example.musicapi.services.definitions.ILikeService;
import com.example.musicapi.services.implementations.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("music-api/likes")
@AllArgsConstructor
public class LikeController {

  private final LikeService likeService;

  @PostMapping("/like")
  public ResponseEntity<String> like(@RequestBody LikeDto likeDto) {
    likeService.likeSong(likeDto.userId(), likeDto.songId());
    return ResponseEntity.ok("Liked");
  }

  @DeleteMapping("/unlike")
  public ResponseEntity<String> unlike(@RequestBody LikeDto likeDto) {
    likeService.unlikeSong(likeDto.userId(), likeDto.songId());
    return ResponseEntity.ok("Unliked");
  }

  @GetMapping("/count")
  public long getLikes(@RequestParam Long songId) {
    return likeService.getLikeCount(songId);
  }

  @GetMapping("/has-liked")
  public boolean hasLiked(@RequestParam Long userId, @RequestParam Long songId) {
    return likeService.hasUserLiked(userId, songId);
  }
}
