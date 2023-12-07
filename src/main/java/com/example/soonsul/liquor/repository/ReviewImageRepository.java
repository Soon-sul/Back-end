package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Review;
import com.example.soonsul.liquor.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    void deleteAllByReview(Review review);
    List<ReviewImage> findAllByReview(Review review);
}
