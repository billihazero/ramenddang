package com.example.ramenddang.review.service;

import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.repository.RamenRepository;
import com.example.ramenddang.review.dto.ReviewDTO;
import com.example.ramenddang.review.entity.Review;
import com.example.ramenddang.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final RamenRepository ramenRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Value("${review.image.dir}")
    private String reviewDir;

    @Value("${ramen.image.url}")
    private String reviewUrl;

    public ReviewService(RamenRepository ramenRepository, MemberRepository memberRepository, ReviewRepository reviewRepository) {
        this.ramenRepository = ramenRepository;
        this.memberRepository = memberRepository;
        this.reviewRepository = reviewRepository;
    }

    public void writeReview(ReviewDTO reviewDTO, long ramenId, Long currentUserId) throws IOException {

        try{
            MultipartFile reviewImg = reviewDTO.reviewImg();

            //파일 이름 생성
            String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + reviewImg.getOriginalFilename();

            //실제 파일 저장 경로
            String filePath = reviewDir + fileName;

            //db에 저장될 경로
            String dbFilePath = reviewUrl + fileName;

            //path객체 생성
            Path path = Paths.get(filePath);

            //디렉토리 생성
            Files.createDirectories(path.getParent());

            //디렉토리에 파일 저장
            Files.write(path, reviewImg.getBytes());

            //ramenId로 Ramen 찾기
            Ramen ramen = ramenRepository.findByRamenId(ramenId);

            //userId로 member 찾기
            Member member = memberRepository.findByUserId(currentUserId);

            //review 객체 생성 및 db 저장
            Review review = new Review();
            review.setRamen(ramen);
            review.setMember(member);
            review.setReviewUrl(dbFilePath);

            reviewRepository.save(review);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("리뷰 저장중 오류가 발생했습니다.",e);
        }
    }

    public List<Review> getAllReview(Long ramenId) {

        Ramen existingRamen = ramenRepository.findByRamenId(ramenId);
        return reviewRepository.findByRamenAndIsDeletedFalse(existingRamen);
    }

    public boolean deleteReview(long reviewId,Long currentUserId) {
        // review 작성한 user정보
        Review existingReivew = reviewRepository.findByReviewId(reviewId);
        Member reviewer = existingReivew.getMember();

        if(reviewer.getUserId().equals(currentUserId)){
            existingReivew.setIsDeleted(true);
            reviewRepository.save(existingReivew);
        }else{
            return false;
        }

        return true;

    }
}
