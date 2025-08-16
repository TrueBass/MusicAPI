package com.example.musicapi.controllers;

import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.dtos.song_dtos.SongInfoLikeDto;
import com.example.musicapi.services.definitions.ISongService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/songs")
public class SongController {

    private final ISongService iSongService;

    @PostMapping("/add")
    public ResponseEntity<SongDto> addSong(@RequestBody CreateSongDto song) {
        SongDto songDto = iSongService.addSong(song);
        return new ResponseEntity<>(songDto, HttpStatus.CREATED);
    }

    @GetMapping("/info/all/{playlistId}")
    public ResponseEntity<List<SongInfoDto>> getAllSongsInfo(@PathVariable Long playlistId) {
        List<SongInfoDto> songs = iSongService.getAllSongsInfo(playlistId);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/bytes/{songId}")
    public ResponseEntity<byte[]> getSongBytes(@PathVariable Long songId) {
        byte[] songBytes = iSongService.getSongBytes(songId);
        return new ResponseEntity<>(songBytes, HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<List<String>> searchTitles(@RequestParam String title) {
        List<String> songs = iSongService.findByTitle(title);
        return  ResponseEntity.ok(songs);
    }

    @GetMapping("/popular/all")
    public ResponseEntity<List<SongInfoLikeDto>> getAllPopularSongs(@RequestParam Long userId) {
        List<SongInfoLikeDto> songs = iSongService.getAllPopularSongs(userId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/top10")
    public List<SongInfoDto> getTop10Songs(@RequestParam Long userId) {
      return iSongService.getTop10Songs(userId);
    }
}
