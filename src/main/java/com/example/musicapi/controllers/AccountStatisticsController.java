package com.example.musicapi.controllers;

import com.example.musicapi.dtos.stats_dtos.AccountStatsDto;
import com.example.musicapi.services.implementations.AccountStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/stats")
public class AccountStatisticsController {
  private AccountStatisticsService accountStatisticsService;

  @GetMapping
  public AccountStatsDto getAccountStats(@RequestParam Long userId) {
    return accountStatisticsService.getAccountStats(userId);
  }
}
