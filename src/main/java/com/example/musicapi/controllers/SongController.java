package com.example.musicapi.controllers;

import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.dtos.song_dtos.SongInfoLikeDto;
import com.example.musicapi.services.definitions.ISongService;
import com.example.musicapi.services.implementations.SongService;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/songs")
public class SongController {

    private final ISongService iSongService;
    private final SongService songService;

    @PostMapping("/add")
    public ResponseEntity<SongDto> addSong(@RequestBody CreateSongDto song) {
        SongDto songDto = iSongService.addSong(song);
        return new ResponseEntity<>(songDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{songId}")
    public ResponseEntity<String> deleteSong(@PathVariable Long songId) {
        iSongService.deleteSong(songId);
        return ResponseEntity.ok("Deleted Song with ID " + songId);
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
    public  ResponseEntity<List<String>> searchTitles(@RequestParam String query) {
        List<String> songs = iSongService.findByTitle(query);
        return  ResponseEntity.ok(songs);
    }

    @GetMapping()
    public ResponseEntity<List<SongInfoLikeDto>> searchSimilarByQuery(@RequestParam String query, @RequestParam Long userId) {
        List<SongInfoLikeDto> songs = iSongService.findByQuery(query,  userId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("popular/all")
    public ResponseEntity<List<SongInfoLikeDto>> getAllPopularSongs(
            @RequestParam Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int limit) {

        List<SongInfoLikeDto> songs = iSongService.getAllPopularSongsPage(userId, cursor, limit);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/top5")
    public List<SongInfoDto> getTopFiveSongs(@RequestParam Long userId) {
      return iSongService.getTopFiveSongs(userId);
    }
}
