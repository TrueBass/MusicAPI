package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.playlist_dtos.CreatePlaylistDto;
import com.example.musicapi.dtos.playlist_dtos.PlaylistDto;

import java.util.List;


public interface IPlaylistService {
    PlaylistDto createPlaylist(CreatePlaylistDto playlistDto);
    List<PlaylistDto> getAllPlaylists(Long userId);
    void deletePlaylist(Long playlistId);
}
