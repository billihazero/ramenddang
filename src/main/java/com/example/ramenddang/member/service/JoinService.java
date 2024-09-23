package com.example.ramenddang.member.service;

import com.example.ramenddang.member.dto.JoinDTO;
import com.example.ramenddang.member.entity.Member;
import com.example.ramenddang.member.entity.Profile;
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
        String userLoginId = joinDTO.userLoginId();
        String userPasswd = joinDTO.userPasswd();
        String userPhone = joinDTO.userPhone();

        String userName = joinDTO.userName();
        String userNickname = joinDTO.userNickname();
        String userEmail = joinDTO.userEmail();

        Member member = new Member();

        member.setUserLoginId(userLoginId);
        member.setUserPasswd(bCryptPasswordEncoder.encode(userPasswd));
        member.setUserPhone(userPhone);

        member.setUserName(userName);
        member.setUserNickname(userNickname);
        member.setUserEmail(userEmail);

        member.setUserRole("ROLE_USER");

        memberRepository.save(member);
    }
}
