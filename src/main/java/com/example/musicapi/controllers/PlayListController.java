package com.example.musicapi.controllers;

import com.example.musicapi.dtos.playlist_dtos.CreatePlaylistDto;
import com.example.musicapi.dtos.playlist_dtos.PlaylistDto;
import com.example.musicapi.entities.Playlist;
import com.example.musicapi.services.definitions.IPlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/playlist")
public class PlayListController {

    private IPlaylistService playlistService;

    @PostMapping ("/create")
    public ResponseEntity<PlaylistDto> createPlaylist(@RequestBody CreatePlaylistDto playlist) {
        PlaylistDto playlistDto = playlistService.createPlaylist(playlist);
        return new ResponseEntity<>(playlistDto, HttpStatus.CREATED);
    }

    @GetMapping ("/get-all")
    public ResponseEntity<List<PlaylistDto>> getAllPlaylists(@RequestParam Long userId) {
        List<PlaylistDto> playlistDto = playlistService.getAllPlaylists(userId);
        return new ResponseEntity<>(playlistDto, HttpStatus.OK);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<Void> deletePlaylist(@RequestParam Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
