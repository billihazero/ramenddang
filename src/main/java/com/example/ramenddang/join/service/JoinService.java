package com.example.ramenddang.join.service;

import com.example.ramenddang.join.dto.JoinDTO;
import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class JoinService {
    private final MemberRepository memberRepository;


    public JoinService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //아이디 존재하는지 확인. 존재하면 true
    public boolean checkId(String userLoginId) {
        return memberRepository.existsByUserLoginId(userLoginId);
    }

    //member 회원가입
    public void joinMember(Member member) {
        memberRepository.save(member);
    }

    //admin 회원가입
    public void joinAdmin(Member member) {
        memberRepository.save(member);
    }



}
