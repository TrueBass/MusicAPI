package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.playlist_dtos.CreatePlaylistDto;
import com.example.musicapi.dtos.playlist_dtos.PlaylistDto;
import com.example.musicapi.entities.Playlist;
import com.example.musicapi.repositories.IPlayListRepository;
import com.example.musicapi.repositories.ISongRepository;
import com.example.musicapi.services.definitions.IPlaylistService;
import com.example.musicapi.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class PlayListService implements IPlaylistService {

    private final IPlayListRepository playListRepository;

    @Override
    public PlaylistDto createPlaylist(CreatePlaylistDto playlistDto) {
        Playlist playlist = Mapper.MapToPlaylist(playlistDto);
        Date date = Date.valueOf(LocalDate.now());
        playlist.setCreatedAt(date);
        playlist.setModifiedAt(date);// do zmiany pozniej (nullable)
        Playlist savedPlaylist = playListRepository.save(playlist);
        return Mapper.MapToPlaylistDto(savedPlaylist);
    }

}
