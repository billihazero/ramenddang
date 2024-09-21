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

    public Member getMyPage(Long userId) {

         Member memberData =  memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

         return memberData;
    }
}
