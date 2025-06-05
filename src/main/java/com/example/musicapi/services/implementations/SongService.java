package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.Song;
import com.example.musicapi.repositories.IPlayListRepository;
import com.example.musicapi.repositories.ISongRepository;
import com.example.musicapi.services.definitions.IPlaylistService;
import com.example.musicapi.services.definitions.ISongService;
import com.example.musicapi.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongService implements ISongService {

    private final ISongRepository songRepository;
    private final IPlayListRepository playListRepository;

    @Override
    public SongDto addSong(CreateSongDto songDto) {
        Song song = Mapper.MapToSong(songDto);
        song.setAddedAt(Date.valueOf(LocalDate.now()));
        song.setLikes(0L);
        Playlist playlist = playListRepository.getReferenceById(songDto.playlistId());
        song.getPlaylists().add(playlist);
        Song savedSong = songRepository.save(song);
        return Mapper.MapToSongDto(savedSong);
    }

    @Override
    public List<String> findByTitle(String title) {
        return songRepository.findByTitle(title)
                .stream()
                .map(Song::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<SongInfoDto> getAllSongsInfo(Long playlistId) {
        return songRepository.getAllWithoutData(playlistId);
    }

    @Override
    public byte[] getSongBytes(Long songId) {
        return songRepository.getDataById(songId);
    }
}
