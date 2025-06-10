package com.example.musicapi.dtos.user_dtos;

public record UpdateSocialCreditDto(
        Long userId,
        int changedSocialCredit
) { }
