package com.example.ramenddang.mypage;

import com.example.ramenddang.member.entity.Member;
import com.example.ramenddang.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MyPageService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member getMyPage(String userLoginId) {

         Member memberData =  memberRepository.findByUserLoginId(userLoginId);

         return memberData;
    }

    public Member updateMyPage(String userLoginId, MyPageUpdateDTO myPageUpdateDTO) {
        
        //기존 회원정보 조회
        Member existingMember = memberRepository.findByUserLoginId(userLoginId);

        // 기존 비밀번호와 DTO로 받은 비밀번호 비교
        if (!passwordEncoder.matches(myPageUpdateDTO.userPasswd(), existingMember.getUserPasswd())) {
            throw new IllegalArgumentException("Password does not match");
        }

        //업데이트
        existingMember.setUserPhone(myPageUpdateDTO.userPhone());
        existingMember.setUserName(myPageUpdateDTO.userName());
        existingMember.setUserNickname(myPageUpdateDTO.userNickname());
        existingMember.setUserEmail(myPageUpdateDTO.userEmail());

        return memberRepository.save(existingMember);


    }
}
