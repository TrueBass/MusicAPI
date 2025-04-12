package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String email;

    int socialCredit = 0;
    boolean isAdmin = false;
    boolean isBanned = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Playlist> playlists;
}
