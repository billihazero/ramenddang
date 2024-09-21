package com.example.ramenddang.mypage;

public record UpdateMemberDTO(
        String userPasswd,
        String userPhone,

        String userName,
        String userNickname,
        String userEmail
){}
