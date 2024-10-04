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

    public Member updateMember(Member currentMember, UpdateMemberDTO updateMemberDTO) {

        //업데이트
        currentMember.setUserPhone(updateMemberDTO.userPhone());
        currentMember.setUserName(updateMemberDTO.userName());
        currentMember.setUserNickname(updateMemberDTO.userNickname());
        currentMember.setUserEmail(updateMemberDTO.userEmail());

        //db 저장
        return memberRepository.save(currentMember);


    }

    public void deleteMember(Member currentMember) {
        
        //member 객체 isDeleted = true 로 변경
        currentMember.setIsDeleted(true);
        
        //db 저장
        memberRepository.save(currentMember);
            
    }
}
