package com.example.musicapi.services.definitions;

import com.example.musicapi.dtos.stats_dtos.AccountStatsDto;

public interface IAccountStatisticsService {
  AccountStatsDto getAccountStats(Long userId);
}
