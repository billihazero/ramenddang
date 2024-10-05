package com.example.ramenddang.review.controller;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.login.dto.MemberDetails;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.repository.RamenRepository;
import com.example.ramenddang.review.dto.ReviewDTO;
import com.example.ramenddang.review.entity.Review;
import com.example.ramenddang.review.repository.ReviewRepository;
import com.example.ramenddang.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    private final RamenRepository ramenRepository;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, MemberRepository memberRepository, RamenRepository ramenRepository, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.memberRepository = memberRepository;
        this.ramenRepository = ramenRepository;
        this.reviewRepository = reviewRepository;
    }

    // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        return memberDetails.getUserId();
    }

    //리뷰 작성
    @PostMapping("/write/{ramenId}")
    public ResponseEntity<String> writeReview(@PathVariable("ramenId") long ramenId, ReviewDTO reviewDTO) throws IOException {

        Long currentUserId = getCurrentUserId();

        Member currentMember = memberRepository.findByUserId(currentUserId);
        Ramen currentRamen = ramenRepository.findByRamenId(ramenId);

        reviewService.writeReview(reviewDTO, currentMember, currentRamen);

        return ResponseEntity.ok("리뷰 작성 성공하였습니다.");
    }

    //리뷰 리스트
    @GetMapping("/list/{ramenId}")
    public ResponseEntity<List<Review>> getAllReview(@PathVariable("ramenId") long ramenId) throws IOException {
        Ramen currentRamen = ramenRepository.findByRamenId(ramenId);

        List<Review> reviewList = reviewService.getAllReview(currentRamen);

        return ResponseEntity.ok(reviewList);
    }

    //리뷰 삭제
    @PutMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") long reviewId) throws IOException {

        Long currentUserId = getCurrentUserId();
        Member currentMember = memberRepository.findByUserId(currentUserId);

        Review review = reviewRepository.findByReviewId(reviewId);
        boolean isDeleted = reviewService.deleteReview(currentMember, review);

        if (isDeleted) {
            return ResponseEntity.ok("리뷰 삭제 성공하였습니다.");
        }
        return ResponseEntity.status(404).body("해당 정보를 찾지 못하였습니다.");
    }
}
