package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.playlist_dtos.CreatePlaylistDto;
import com.example.musicapi.dtos.playlist_dtos.PlaylistDto;
import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.User;
import com.example.musicapi.exceptions.NotFoundException;
import com.example.musicapi.repositories.IPlayListRepository;
import com.example.musicapi.repositories.ISongRepository;
import com.example.musicapi.repositories.IUserRepository;
import com.example.musicapi.services.definitions.IPlaylistService;
import com.example.musicapi.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayListService implements IPlaylistService {

    private final IPlayListRepository playListRepository;
    private final IUserRepository userRepository;

    @Override
    public PlaylistDto createPlaylist(CreatePlaylistDto playlistDto) {
        Playlist playlist = Mapper.MapToPlaylist(playlistDto);
        playlist.setCreatedAt(Date.valueOf(LocalDate.now()));
        User user = userRepository.getReferenceById(playlistDto.userId());
        playlist.setUser(user);
        Playlist savedPlaylist = playListRepository.save(playlist);
        return Mapper.MapToPlaylistDto(savedPlaylist);
    }

    @Override
    public List<PlaylistDto> getAllPlaylists(Long userId) {
        List<Playlist> playlists = playListRepository.findByUserId(userId);
        return playlists.stream()
                .map(Mapper::MapToPlaylistDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePlaylist(Long playlistId) {
        if(playListRepository.existsById(playlistId)) {
            playListRepository.deleteById(playlistId);
        }
        else {
            throw new NotFoundException("Playlist not found");
        }
    }

    @Override
    public void changeVisibility(Long playlistId, Boolean visibility) {
        Playlist playlist = playListRepository.findById(playlistId).orElseThrow(
                () -> new NotFoundException("Playlist not found")
        );

        playlist.setPrivate(visibility);
        playListRepository.save(playlist);
    }

}
