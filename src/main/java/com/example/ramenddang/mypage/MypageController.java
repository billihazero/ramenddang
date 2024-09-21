package com.example.ramenddang.mypage;

import com.example.ramenddang.member.entity.Member;
import com.example.ramenddang.member.jwt.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class MypageController {

    private final JWTUtil jwtUtil;
    private final MyPageService myPageService;

    public MypageController(JWTUtil jwtUtil, MyPageService myPageService) {
        this.jwtUtil = jwtUtil;
        this.myPageService = myPageService;
    }

    // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
    private String getCurrentUserLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // 인증된 사용자의 userLoginId
    }

    @GetMapping("/memberget")
    public ResponseEntity<Member> getMyPage() {
        String userLoginId = getCurrentUserLoginId();

        // userLoginId를 통해 사용자 정보 조회
        Member memberData = myPageService.getMyPage(userLoginId);

        return ResponseEntity.ok(memberData);
    }

    @PutMapping("/memberupdate")
    public ResponseEntity<Member> updateMember(@RequestBody UpdateMemberDTO updateMemberDTO) {

        String userLoginId = getCurrentUserLoginId();

        Member updateMemberData = myPageService.updateMember(userLoginId, updateMemberDTO);

        return ResponseEntity.ok(updateMemberData);
    }

    @PutMapping("/memberdelete")
    public ResponseEntity<String> deleteMember(@RequestBody DeleteMemberDTO deleteMemberDTO) {
        String userLoginId = getCurrentUserLoginId();

        boolean isDeleted = myPageService.deleteMember(userLoginId, deleteMemberDTO);

        if (isDeleted) {
            return ResponseEntity.ok("회원 정보가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(401).body("비밀번호가 일치하지 않습니다.");

        }

    }

}
