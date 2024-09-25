package com.example.ramenddang.mypage.service;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.mypage.dto.DeleteMemberDTO;
import com.example.ramenddang.mypage.dto.UpdateMemberDTO;
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

    public Member getMyPage(Long userId) {

         Member memberData =  memberRepository.findByUserId(userId);

         return memberData;
    }

    public Member updateMember(Long userId, UpdateMemberDTO updateMemberDTO) {

        //기존 회원정보 조회
        Member existingMember = memberRepository.findByUserId(userId);

        // 기존 비밀번호와 DTO로 받은 비밀번호 비교
        if (!passwordEncoder.matches(updateMemberDTO.userPasswd(), existingMember.getUserPasswd())) {
            throw new IllegalArgumentException("Password does not match");
        }

        //업데이트
        existingMember.setUserPhone(updateMemberDTO.userPhone());
        existingMember.setUserName(updateMemberDTO.userName());
        existingMember.setUserNickname(updateMemberDTO.userNickname());
        existingMember.setUserEmail(updateMemberDTO.userEmail());

        return memberRepository.save(existingMember);


    }

    public boolean deleteMember(Long userId, DeleteMemberDTO deleteMemberDTO) {
        Member existingMember = memberRepository.findByUserId(userId);

        String inputPasswd = deleteMemberDTO.inputPasswd();
        if (passwordEncoder.matches(inputPasswd, existingMember.getUserPasswd())) {
            existingMember.setIsDeleted(true);
            memberRepository.save(existingMember);

            return true;
        }else{
            throw new IllegalArgumentException("Password does not match");
        }

    }
}
