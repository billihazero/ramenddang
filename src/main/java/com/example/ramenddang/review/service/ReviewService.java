package com.example.ramenddang.review.service;

import com.example.ramenddang.exception.FileUploadException;
import com.example.ramenddang.join.entity.Member;
import com.example.ramenddang.join.repository.MemberRepository;
import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.ramen.repository.RamenRepository;
import com.example.ramenddang.review.dto.ReviewDTO;
import com.example.ramenddang.review.entity.Review;
import com.example.ramenddang.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Value("${review.image.dir}")
    private String reviewDir;

    @Value("${ramen.image.url}")
    private String reviewUrl;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //리뷰 작성
    @Transactional
    public void writeReview(ReviewDTO reviewDTO,  Member currentMember, Ramen currentRamen) throws IOException {

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

            //review 객체 생성 및 db 저장
            Review review = new Review();

            review.setRamen(currentRamen);
            review.setMember(currentMember);
            review.setReviewUrl(dbFilePath);

            reviewRepository.save(review);

        }catch (IOException e) {
            throw new FileUploadException("파일 업로드 중 오류가 발생했습니다.");
        }catch (Exception e) {
            throw new RuntimeException("리뷰 저장 중 알 수 없는 오류가 발생했습니다.", e);
        }
    }

    //리뷰 리스트 
    public List<Review> getAllReview(Ramen currentRamen) {

        return reviewRepository.findByRamenAndIsDeletedFalse(currentRamen);
    }

    //리뷰 삭제 isDeleted = true로 변경
    public boolean deleteReview(Member currentMember, Review currentReview) {

        //리뷰 작성자와 삭제 요청한 사람이 같은지 확인
        if(currentReview.getMember().equals(currentMember)){
            currentReview.setIsDeleted(true);
            reviewRepository.save(currentReview);
        }else{
            return false;
        }
        return true;

    }
}
