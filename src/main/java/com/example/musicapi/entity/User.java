package com.example.musicapi.entity;

import jakarta.persistence.*;
import lombok.*;

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

    int socialCredit;
    boolean is_admin;
    boolean isBanned;
}
