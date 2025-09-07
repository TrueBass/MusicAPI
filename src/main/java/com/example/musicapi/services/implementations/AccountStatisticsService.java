package com.example.musicapi.services.implementations;

import com.example.musicapi.dtos.song_dtos.SongInfoLikeDto;
import com.example.musicapi.dtos.stats_dtos.AccountStatsDto;
import com.example.musicapi.entities.Playlist;
import com.example.musicapi.entities.User;
import com.example.musicapi.exceptions.NotFoundException;
import com.example.musicapi.repositories.IPlayListRepository;
import com.example.musicapi.repositories.ISongRepository;
import com.example.musicapi.repositories.IUserRepository;
import com.example.musicapi.services.definitions.IAccountStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountStatisticsService implements IAccountStatisticsService {
  IPlayListRepository playListRepository;
  ISongRepository songRepository;
  IUserRepository userRepository;

  @Override
  public AccountStatsDto getAccountStats(Long userId) {

    User user = userRepository.findById(userId).orElseThrow(
            () -> new NotFoundException("User not found."));
    List<SongInfoLikeDto> allUserSongs = songRepository.getAllSongsOfUser(userId);

    long numOfPlaylists = user.getPlaylists().size();
    long numberOfSongs = allUserSongs.size();
    long totalLikes = allUserSongs.stream()
            .mapToLong(SongInfoLikeDto::getLikes)
            .sum();
    double avgLikesPerSong = numberOfSongs == 0 ? 0 : totalLikes / (double) numberOfSongs;

    Playlist largestPlaylist = playListRepository.findLargestPlaylist(userId)
            .orElseThrow(() -> new NotFoundException("Largest Playlist not found"));

    AccountStatsDto accountStatsDto = new AccountStatsDto(numOfPlaylists, numberOfSongs);
    accountStatsDto.setTotalLikes(totalLikes);
    accountStatsDto.setAvgLikesPerSong(Math.round(avgLikesPerSong * 100.0) / 100.0);
    accountStatsDto.setPlaylistSummary(largestPlaylist.getTitle(),
            largestPlaylist.getSongs().size());
    return accountStatsDto;
  }
}
