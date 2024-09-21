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

    public Member updateMember(String userLoginId, UpdateMemberDTO updateMemberDTO) {
        
        //기존 회원정보 조회
        Member existingMember = memberRepository.findByUserLoginId(userLoginId);

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

    public boolean deleteMember(String userLoginId, DeleteMemberDTO deleteMemberDTO) {
        Member existingMember = memberRepository.findByUserLoginId(userLoginId);

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
