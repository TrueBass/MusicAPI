package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

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
    private Date addedAt;

    @Column(nullable = false)
    private Long likes;

    @Column(nullable = false)
    private byte[] data;

    @Column(nullable = false)
    private Long duration;

    @ManyToOne
    @JoinColumn(name = "playlist_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_songs_playlist"))
    private Playlist playlist;
}
