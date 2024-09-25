package com.example.ramenddang.mypage.entity;

import com.example.ramenddang.join.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    //프로필 이미지 경로
    private String profileUrl;
    
    //fk
    @OneToOne
    @JoinColumn(name = "userId")
    @JsonBackReference // 참조 쪽
    private Member member;


}
