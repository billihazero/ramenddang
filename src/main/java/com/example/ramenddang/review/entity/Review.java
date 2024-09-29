package com.example.ramenddang.review.entity;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.ramen.entity.Ramen;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String reviewUrl;
    private LocalDate reviewDate = LocalDate.now();

    private Boolean isDeleted = false;

    //라멘
    @ManyToOne
    @JoinColumn(name="ramenId")
    @JsonBackReference // 참조 쪽
    private Ramen ramen;

    //유저
    @ManyToOne
    @JoinColumn(name="userId")
    @JsonBackReference // 참조 쪽
    private Member member;


}
