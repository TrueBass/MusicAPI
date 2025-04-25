package com.example.musicapi.utils;

import com.example.musicapi.dtos.playlist_dtos.CreatePlaylistDto;
import com.example.musicapi.dtos.playlist_dtos.UpdatePlaylistDto;
import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.playlist_dtos.PlaylistDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.user_dtos.UserAuthDto;
import com.example.musicapi.dtos.user_dtos.UserDto;
import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.Song;
import com.example.musicapi.entities.User;

import java.util.HashSet;

public final class Mapper {
    public static Playlist MapToPlaylist(CreatePlaylistDto dto) {
        return Playlist.builder()
                .title(dto.title())
                .isPrivate(dto.isPrivate())
                .build();
    }

    public static Playlist MapToPlaylistDto(UpdatePlaylistDto dto) {
        return Playlist.builder()
                .id(dto.id())
                .title(dto.title())
                .isPrivate(dto.isPrivate())
                .build();
    }

    public static PlaylistDto MapToPlaylistDto(Playlist playlist) {
        return new PlaylistDto(
                playlist.getId(),
                playlist.getTitle(),
                playlist.getCreatedAt(),
                playlist.getModifiedAt(),
                playlist.isPrivate(),
                playlist.getUser().getId());
    }

    public static Song MapToSong(CreateSongDto dto) {
        return Song.builder()
                .title(dto.title())
                .author(dto.author())
                .duration(dto.duration())
                .data(dto.data())
                .genre(dto.genre())
                .build();
    }

    public static SongDto MapToSongDto(Song song) {
        return new SongDto(
                song.getId(),
                song.getTitle(),
                song.getAuthor(),
                song.getAddedAt(),
                song.getLikes(),
                song.getData(),
                song.getDuration(),
                song.getGenre(),
                song.getPlaylist().getId());
    }

    public static UserDto MapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getSocialCredit(),
                user.isBanned()
        );
    }

    public static User MapToUser(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .socialCredit(userDto.getSocialCredit())
                .isBanned(userDto.isBanned())
                .build();
    }

    public static User MapToUser(UserAuthDto userAuthDto){
        return User.builder()
                .username(userAuthDto.getUsername())
                .email(userAuthDto.getEmail())
                .password(userAuthDto.getPassword())
                .roles(new HashSet<>())
                .build();
    }
}
