package com.example.ramenddang.mypage.controller;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.mypage.dto.DeleteMemberDTO;
import com.example.ramenddang.mypage.dto.UpdateMemberDTO;
import com.example.ramenddang.mypage.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
public class MypageController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MypageController(MyPageService myPageService, MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.myPageService = myPageService;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        return memberDetails.getUserId();
    }

    //회원정보 조회
    @GetMapping("/get")
    public ResponseEntity<Member> getMyPage() {
        Long userId = getCurrentUserId();

        // userLoginId를 통해 사용자 정보 조회
        Member memberData = myPageService.getMyPage(userId);

        return ResponseEntity.ok(memberData);
    }

    //회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateMember(@RequestBody UpdateMemberDTO updateMemberDTO) {
        Long userId = getCurrentUserId();
        
        //사용자 인증 정보 통해 member 객체 찾기
        Member currentMember = memberRepository.findByUserId(userId);

        // 기존 비밀번호와 DTO로 받은 비밀번호 비교
        if (!passwordEncoder.matches(updateMemberDTO.userPasswd(), currentMember.getUserPasswd())) {
          return ResponseEntity.status(400).body("비밀번호가 일치하지 않습니다.");
        }

        Member updateMemberData = myPageService.updateMember(currentMember, updateMemberDTO);

        return ResponseEntity.ok(updateMemberData);
    }

    //회원정보 삭제
    //isDeleted= true로 변경한다.
    @PutMapping("/delete")
    public ResponseEntity<String> deleteMember(@RequestBody DeleteMemberDTO deleteMemberDTO) {

        Long userId = getCurrentUserId();

        //사용자 인증 정보 통해 member 객체 찾기
        Member currentMember = memberRepository.findByUserId(userId);

        // 기존 비밀번호와 DTO로 받은 비밀번호 비교
        if (!passwordEncoder.matches(deleteMemberDTO.inputPasswd(), currentMember.getUserPasswd())) {
            return ResponseEntity.status(400).body("비밀번호가 일치하지 않습니다.");
        }

        myPageService.deleteMember(currentMember);

        return ResponseEntity.ok("회원 정보가 성공적으로 삭제되었습니다.");

    }



}
