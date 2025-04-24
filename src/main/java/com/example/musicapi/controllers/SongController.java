package com.example.musicapi.controllers;

import com.example.musicapi.entities.Song;
import com.example.musicapi.repositories.ISongRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("music-api/songs")
public class SongController {

    private final ISongRepository iSongRepository;

    @GetMapping("/search")
    public List<String> searchTitles(@RequestParam String title) {
        return iSongRepository.findByTitle(title)
                .stream()
                .map(Song::getTitle)
                .collect(Collectors.toList());
    }
}
