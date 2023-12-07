package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Review;
import com.example.soonsul.liquor.entity.ReviewImage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    void deleteAllByReview(Review review);
    List<ReviewImage> findAllByReview(Review review);

    @Query(nativeQuery = true,
            value="SELECT r.image FROM review_image r WHERE r.review_id = :reviewId")
    List<String> findAllImage(@Param("reviewId") Long reviewId);
}
