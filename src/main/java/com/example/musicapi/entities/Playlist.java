package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;
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
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private Date modifiedAt;

    @Column(nullable = false)
    private boolean isPrivate = false;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Song> songs;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_playlists_user"))
    private User user;
}