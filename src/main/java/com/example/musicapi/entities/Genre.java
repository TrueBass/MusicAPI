package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="genres")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "genre")
  private Set<Song> songs = new HashSet<>();
}
