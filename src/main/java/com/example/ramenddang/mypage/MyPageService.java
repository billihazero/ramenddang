package com.example.ramenddang.mypage;

import com.example.ramenddang.member.entity.Member;
import com.example.ramenddang.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    private final MemberRepository memberRepository;

    public MyPageService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMyPage(String userLoginId) {

         Member memberData =  memberRepository.findByUserLoginId(userLoginId);

         return memberData;
    }
}
