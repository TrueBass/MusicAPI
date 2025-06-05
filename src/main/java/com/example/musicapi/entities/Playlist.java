package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
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
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;

    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date modifiedAt;

    @Column(nullable = false)
    private boolean isPrivate = false;

    @ManyToMany
    @JoinTable( name = "playlist_sons",
    joinColumns = @JoinColumn(name = "playlist_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> songs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_playlists_user"))
    private User user;
}