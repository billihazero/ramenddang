package com.example.ramenddang.mypage.dto;

import org.springframework.web.multipart.MultipartFile;

public record ProfileDTO(
        MultipartFile profileImg
) {
}
