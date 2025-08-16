package com.example.musicapi.entities;

import com.example.musicapi.enums.MusicGenre;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="songs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String uploader;

    @Column(nullable = false)
    private Date addedAt;

    @Column(nullable = false)
    private byte[] data;

    @Column(nullable = false)
    private Long duration;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private MusicGenre genre = MusicGenre.OTHER;

    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists = new HashSet<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();
}
