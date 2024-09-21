package com.example.ramenddang.mypage;

import com.example.ramenddang.member.entity.Member;
import com.example.ramenddang.member.jwt.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MypageController {

    private final JWTUtil jwtUtil;
    private final MyPageService myPageService;

    public MypageController(JWTUtil jwtUtil, MyPageService myPageService) {
        this.jwtUtil = jwtUtil;
        this.myPageService = myPageService;
    }

    @GetMapping("/memberget")
    public ResponseEntity<Member> getMyPage(@RequestHeader("Authorization")String authorizationHeader) {

        String accessToken = authorizationHeader.substring(7);
        Long userId =jwtUtil.getUserId(accessToken);

        Member memberData = myPageService.getMyPage(userId);

        return ResponseEntity.ok(memberData);

    }
}
