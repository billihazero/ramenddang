package com.example.ramenddang.mypage.controller;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.mypage.dto.DeleteMemberDTO;
import com.example.ramenddang.mypage.dto.UpdateMemberDTO;
import com.example.ramenddang.mypage.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class MypageController {

    private final MyPageService myPageService;

    public MypageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }


    // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        Long userId = memberDetails.getUserId();
        String userLoginId = authentication.getName();

        System.out.println("userId = " + userId + " userLoginId = " + userLoginId);

        return userId;

    }

    @GetMapping("/memberget")
    public ResponseEntity<Member> getMyPage() {
        Long userId = getCurrentUserId();

        // userLoginId를 통해 사용자 정보 조회
        Member memberData = myPageService.getMyPage(userId);

        return ResponseEntity.ok(memberData);
    }

    @PutMapping("/memberupdate")
    public ResponseEntity<Member> updateMember(@RequestBody UpdateMemberDTO updateMemberDTO) {

        Long userId = getCurrentUserId();

        Member updateMemberData = myPageService.updateMember(userId, updateMemberDTO);

        return ResponseEntity.ok(updateMemberData);
    }

    @PutMapping("/memberdelete")
    public ResponseEntity<String> deleteMember(@RequestBody DeleteMemberDTO deleteMemberDTO) {

        Long userId = getCurrentUserId();

        boolean isDeleted = myPageService.deleteMember(userId, deleteMemberDTO);

        if (isDeleted) {
            return ResponseEntity.ok("회원 정보가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(401).body("비밀번호가 일치하지 않습니다.");

        }

    }

}
