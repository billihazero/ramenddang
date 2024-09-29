package com.example.ramenddang.review.dto;

import org.springframework.web.multipart.MultipartFile;

public record ReviewDTO(
        MultipartFile reviewImg
) {
}
