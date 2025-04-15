package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "refresh_tokens")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String token;
    Date expiresOn;
    Date createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
