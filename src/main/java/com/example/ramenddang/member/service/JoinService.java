package com.example.ramenddang.member.service;

import com.example.ramenddang.member.dto.JoinDTO;
import com.example.ramenddang.member.entity.Member;
import com.example.ramenddang.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public void joinProcess(JoinDTO joinDTO) {

        String memberId = joinDTO.getMemberId();
        String memberPw = joinDTO.getMemberPw();
        String memberNm = joinDTO.getMemberNm();
        String memberNknm = joinDTO.getMemberNknm();
        String memberTel = joinDTO.getMemberTel();
        String memberEmail = joinDTO.getMemberEmail();

        Boolean isExist = memberRepository.existsByMemberId(memberId);
        if(isExist){
            throw new IllegalArgumentException("이미 존재하는 회원 입니다.");
        }

        Member member = new Member();

        member.setMemberId(memberId);

        member.setMemberPw(bCryptPasswordEncoder.encode(memberPw));
        member.setMemberNm(memberNm);
        member.setMemberNknm(memberNknm);
        member.setMemberTel(memberTel);
        member.setMemberEmail(memberEmail);

        member.setRole("ROLE_USER");

        memberRepository.save(member);


    }
}
