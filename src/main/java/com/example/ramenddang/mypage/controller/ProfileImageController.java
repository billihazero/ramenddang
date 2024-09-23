package com.example.ramenddang.mypage.controller;

import com.example.ramenddang.mypage.dto.ProfileDTO;
import com.example.ramenddang.mypage.service.ProfileImageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    public ProfileImageController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
    private String getCurrentUserLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // 인증된 사용자의 userLoginId
    }

    @PostMapping("/uploadprofile")
    public String getProfileImage(ProfileDTO profileDTO) {

        String currentUserLoginId = getCurrentUserLoginId();
        profileImageService.getProfileImage(profileDTO);
        return "";
    }
}
