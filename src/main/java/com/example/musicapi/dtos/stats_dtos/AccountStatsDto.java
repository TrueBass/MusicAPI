package com.example.musicapi.dtos.stats_dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatsDto {
  public AccountStatsDto(long totalPlaylists, long totalSongs) {
    this.totalPlaylists = totalPlaylists;
    this.totalSongs = totalSongs;
  }
  private PlaylistSummary playlistSummary = new PlaylistSummary();
  private long totalLikes;
  private long totalSongs;
  private long totalPlaylists;
  private double avgLikesPerSong;
  public void setPlaylistSummary(String title, long songsCount) {
    playlistSummary.setTitle(title);
    playlistSummary.setSongsCount(songsCount);
  }
}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class PlaylistSummary {
  String title;
  long songsCount;
}
