package com.example.soonsul.promotion.repository;

import com.example.soonsul.promotion.entity.Promotion;
import com.example.soonsul.promotion.entity.Zzim;
import com.example.soonsul.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzimRepository extends JpaRepository<Zzim, Long> {
    boolean existsByUserAndPromotion(User user, Promotion promotion);
    void deleteByUserAndPromotion(User user, Promotion promotion);
}
