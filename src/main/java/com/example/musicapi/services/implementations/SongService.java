package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.song_dtos.CreateSongDto;
import com.example.musicapi.dtos.song_dtos.SongDto;
import com.example.musicapi.dtos.song_dtos.SongInfoDto;
import com.example.musicapi.dtos.song_dtos.SongInfoLikeDto;
import com.example.musicapi.entities.Genre;
import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.Song;
import com.example.musicapi.exceptions.NotFoundException;
import com.example.musicapi.repositories.IGenreRepository;
import com.example.musicapi.repositories.ILikeRepository;
import com.example.musicapi.repositories.IPlayListRepository;
import com.example.musicapi.repositories.ISongRepository;
import com.example.musicapi.services.definitions.ILikeService;
import com.example.musicapi.services.definitions.IPlaylistService;
import com.example.musicapi.services.definitions.ISongService;
import com.example.musicapi.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final LikeService likeService;
    private final IGenreRepository iGenreRepository;

    @Override
    public SongDto addSong(CreateSongDto songDto) {
        Genre genre = iGenreRepository.findById(songDto.genreId()).orElseThrow(
                () -> new NotFoundException("genre not found"));
        Song song = Mapper.MapToSong(songDto);
        song.setAddedAt(Date.valueOf(LocalDate.now()));
        song.setGenre(genre);

        Playlist playlist = playListRepository.getReferenceById(songDto.playlistId());
        playlist.getSongs().add(song);

        song.getPlaylists().add(playlist);
        Song savedSong = songRepository.save(song);

        playListRepository.save(playlist);

        return Mapper.MapToSongDto(savedSong);
    }

    @Transactional
    public void deleteSong(Long songId) {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if (optionalSong.isEmpty()) {
            throw new NotFoundException("Song with ID " + songId + " not found.");
        }
        Song song = optionalSong.get();

        for (Playlist playlist : song.getPlaylists()) {
            playlist.getSongs().remove(song);
        }

        songRepository.delete(song);
    }

    @Override
    public List<String> findByTitle(String query) {
        return songRepository.searchByTitleAuthor(query);
    }

    @Override
    public List<SongInfoLikeDto> findByQuery(String query, Long userId) {
        List<SongInfoLikeDto> songs = songRepository.findSongByQuery(query);
        songs.forEach(song ->
            song.setLikedByUser(likeService.hasUserLiked(userId, song.getId()))
        );
        return songs;
    }

    @Override
    public List<SongInfoDto> getAllSongsInfo(Long playlistId) {
        return songRepository.getAllWithoutData(playlistId);
    }

    @Override
    public List<SongInfoLikeDto> getAllPopularSongs(Long userId) {
      return songRepository.getAllPopular(userId);
    }

    @Override
    public List<SongInfoLikeDto> getAllPopularSongsPage(Long userId, Long cursor, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return songRepository.getAllPopularPage(userId, cursor, pageable);
    }

    @Override
    public byte[] getSongBytes(Long songId) {
        return songRepository.getDataById(songId);
    }

    @Override
    public List<SongInfoDto> getTopFiveSongs(Long userId) {
        List<Playlist> playlists = playListRepository.findByUserId(userId);
        Set<Song> allSongs = playlists.stream()
                .flatMap(playlist -> playlist.getSongs().stream())
                .collect(Collectors.toSet());
        return allSongs.stream()
                .sorted(Comparator.comparing(song -> ((Song)song).getLikes().size()).reversed())
                .limit(5).map(s->new SongInfoDto(s.getId(),s.getTitle(),s.getAuthor(),s.getAddedAt(),s.getLikes().size(),s.getDuration(),s.getGenre().getId()))
                .toList();
    }
}
