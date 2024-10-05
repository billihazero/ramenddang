package com.example.ramenddang.mypage.controller;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.mypage.dto.ProfileDTO;
import com.example.ramenddang.mypage.service.ProfileImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class ProfileImageController {

    private final ProfileImageService profileImageService;
    private final MemberRepository memberRepository;

    public ProfileImageController(ProfileImageService profileImageService, MemberRepository memberRepository) {
        this.profileImageService = profileImageService;
        this.memberRepository = memberRepository;
    }

    // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        return memberDetails.getUserId();
    }

    @PostMapping("/uploadprofile")
    public ResponseEntity<?> saveProfileImage(ProfileDTO profileDTO) throws IOException {

        //Member 객체 가져오기
        Long userId = getCurrentUserId();
        Member currentMember = memberRepository.findByUserId(userId);

        profileImageService.saveProfileImage(profileDTO, currentMember);

        // 응답 메시지와 Member 객체를 함께 반환
        Map<String, Object> response = new HashMap<>();
        response.put("message", "프로필 이미지를 저장했습니다.");
        response.put("member", currentMember);

        return ResponseEntity.ok(response);
    }
}
