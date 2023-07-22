package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Review;
import com.example.soonsul.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(nativeQuery = true,
            value="SELECT * FROM review r WHERE r.liquor_id = :liquorId" +
                    " ORDER BY r.review_id DESC")
    Page<Review> findAllByLatest(Pageable pageable, @Param("liquorId") String liquorId);

    @Query(nativeQuery = true,
            value="SELECT * FROM review r WHERE r.liquor_id = :liquorId" +
                    " ORDER BY r.liquor_rating DESC, r.review_id DESC")
    Page<Review> findAllByRating(Pageable pageable, @Param("liquorId") String liquorId);

    Integer countByUser(User user);
    Optional<Review> findByUserAndLiquor(User user, Liquor liquor);
    Integer countByLiquor(Liquor liquor);
    void deleteByUserAndLiquor(User user, Liquor liquor);
}
