package com.example.ramenddang.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    private String profileUrl;

    @OneToOne
    @JoinColumn(name = "userId")
    private Member member;
}
