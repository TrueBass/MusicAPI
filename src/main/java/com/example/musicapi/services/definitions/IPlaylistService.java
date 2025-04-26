package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.playlist_dtos.CreatePlaylistDto;
import com.example.musicapi.dtos.playlist_dtos.PlaylistDto;



public interface IPlaylistService {
    PlaylistDto createPlaylist(CreatePlaylistDto playlistDto);
}
