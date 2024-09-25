package com.example.ramenddang.mypage.controller;

import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.mypage.dto.ProfileDTO;
import com.example.ramenddang.mypage.service.ProfileImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    public ProfileImageController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
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

    @PostMapping("/uploadprofile")
    public ResponseEntity<String> saveProfileImage(ProfileDTO profileDTO) throws IOException {

        Long userId = getCurrentUserId();
        profileImageService.saveProfileImage(profileDTO, userId);
        return ResponseEntity.ok("Profile Image Saved");
    }
}
