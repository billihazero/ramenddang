package com.example.ramenddang.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberIdx;

    private String memberId;
    private String memberPw;
    private String memberNm;
    private String memberNknm;
    private String memberTel;
    private String memberEmail;

    private String role;

}
