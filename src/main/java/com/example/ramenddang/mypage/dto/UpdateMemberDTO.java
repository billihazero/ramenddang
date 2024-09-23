package com.example.ramenddang.mypage.dto;

public record UpdateMemberDTO(
        String userPasswd,
        String userPhone,

        String userName,
        String userNickname,
        String userEmail
){}
