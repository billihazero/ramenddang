package com.example.ramenddang.ramen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RamenPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    private String originalName;
    private String photoUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime deletedAt;

    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "ramenId")
    private Ramen ramen;

}
