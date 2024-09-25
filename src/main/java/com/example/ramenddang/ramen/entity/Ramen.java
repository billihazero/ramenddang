package com.example.ramenddang.ramen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ramen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ramenId;

    private String ramenName;
    private String ramenMenu;

    @Column(length = 3000)
    private String ramenContent;
    private String ramenState;
    private String ramenCity;
    private String ramenAddress;

    private Boolean isDeleted = false;
    
    //ramen 글이 사라지면 사진도 삭제되어야해
    @OneToMany(mappedBy = "ramen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RamenPhoto> ramenPhotos = new ArrayList<>();
    
}
