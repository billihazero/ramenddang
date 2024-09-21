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

    @GetMapping("/memberget")
    public ResponseEntity<Member> getMyPage() {

        // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userLoginId = authentication.getName(); // 인증된 사용자의 userLoginId

        // userLoginId를 통해 사용자 정보 조회
        Member memberData = myPageService.getMyPage(userLoginId);

        return ResponseEntity.ok(memberData);
    }

}
