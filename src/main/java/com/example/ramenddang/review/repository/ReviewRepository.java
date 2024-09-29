package com.example.ramenddang.review.repository;

import com.example.ramenddang.ramen.entity.Ramen;
import com.example.ramenddang.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRamenAndIsDeletedFalse(Ramen ramen);

    Review findByReviewId(long reviewId);
}
