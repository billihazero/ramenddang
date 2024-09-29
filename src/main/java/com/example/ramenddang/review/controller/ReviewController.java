package com.example.ramenddang.review.controller;

import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.review.dto.ReviewDTO;
import com.example.ramenddang.review.entity.Review;
import com.example.ramenddang.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
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

    @PostMapping("/write/{ramenId}")
    public ResponseEntity<String> writeReview(@PathVariable("ramenId") long ramenId, ReviewDTO reviewDTO) throws IOException {
        Long currentUserId = getCurrentUserId();

        reviewService.writeReview(reviewDTO, ramenId, currentUserId);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/list/{ramenId}")
    public ResponseEntity<List<Review>> getAllReview(@PathVariable("ramenId") long ramenId) throws IOException {
        List<Review> reviewList = reviewService.getAllReview(ramenId);

        return ResponseEntity.ok(reviewList);
    }

    @PutMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") long reviewId) throws IOException {

        Long currentUserId = getCurrentUserId();

        boolean isDeleted = reviewService.deleteReview(reviewId, currentUserId);

        if (isDeleted) {
            return ResponseEntity.ok("delete success");
        }
        return ResponseEntity.status(404).body("not found");
    }
}
