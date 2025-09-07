package com.example.musicapi.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="roles")
@Setter @Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
}
