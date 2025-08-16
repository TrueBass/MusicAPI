package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false,
          foreignKey = @ForeignKey(name = "fk_likes_user"))
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "song_id", nullable = false,
          foreignKey = @ForeignKey(name = "fk_likes_song"))
  private Song song;

  @Column(nullable = false)
  private Date likedAt = new Date(); // Optional: track when user liked the song
}
