package com.example.ramenddang.ramen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
