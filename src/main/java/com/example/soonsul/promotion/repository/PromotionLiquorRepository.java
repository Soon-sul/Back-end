package com.example.soonsul.promotion.repository;

import com.example.soonsul.promotion.entity.PromotionLiquor;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionLiquorRepository extends JpaRepository<PromotionLiquor, Long> {

    @Query(nativeQuery = true,
            value="SELECT p.liquor_id FROM promotion_liquor p WHERE p.promotion_id = :promotionId")
    List<String> findByPromotion(@Param("promotionId") Long promotionId);
}
