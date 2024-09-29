package com.example.ramenddang.bookmark.entity;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.ramen.entity.Ramen;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ramenId")
    @JsonBackReference
    private Ramen ramen;
}
