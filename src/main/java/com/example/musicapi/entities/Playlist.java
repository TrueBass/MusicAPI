package com.example.musicapi.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="playlists")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date modifiedAt;

    @Column(nullable = false)
    private boolean isPrivate = false;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Song> songs;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_playlists_user"))
    private User user;
}