package com.example.ramenddang.member.dto;


public record JoinDTO(
    String userLoginId,
    String userPasswd,
    String userPhone,

    String userName,
    String userNickname,
    String userEmail
){

}
