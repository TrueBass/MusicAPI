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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongService implements ISongService {

    private final ISongRepository songRepository;
    private final IPlayListRepository playListRepository;

    @Override
    public SongDto addSong(CreateSongDto songDto) {

        Optional<Song> existingSongOptional = songRepository.findByTitleAndAuthor(songDto.title(), songDto.author());

        Song song;
        if (existingSongOptional.isPresent()) {
            song = existingSongOptional.get();
        } else {
            song = Mapper.MapToSong(songDto);
            song.setAddedAt(Date.valueOf(LocalDate.now()));
            song.setLikes(0L);
        }

        Playlist playlist = playListRepository.getReferenceById(songDto.playlistId());
        playlist.getSongs().add(song);

        song.getPlaylists().add(playlist);
        Song savedSong = songRepository.save(song);

        playListRepository.save(playlist);

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
    public List<SongInfoDto> getAllPopularSongs() {
        return songRepository.getAllPopular();
    }

    @Override
    public byte[] getSongBytes(Long songId) {
        return songRepository.getDataById(songId);
    }

    @Override
    public List<SongInfoDto> getTop10Songs(Long userId) {
        List<Playlist> playlists = playListRepository.findByUserId(userId);
        Set<Song> allSongs = playlists.stream()
                .flatMap(playlist -> playlist.getSongs().stream())
                .collect(Collectors.toSet());
        return allSongs.stream()
                .sorted(Comparator.comparing(Song::getAddedAt).reversed())
                .limit(10).map(s->new SongInfoDto(s.getId(),s.getTitle(),s.getAuthor(),s.getAddedAt(),s.getLikes(),s.getDuration(),s.getGenre()))
                .toList();
    }
}
