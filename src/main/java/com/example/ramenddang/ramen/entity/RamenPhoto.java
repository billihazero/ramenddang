package com.example.ramenddang.ramen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "ramenId")
    @JsonBackReference // 참조 쪽
    private Ramen ramen;

}
