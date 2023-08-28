package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.PrizeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrizeInfoRepository extends JpaRepository<PrizeInfo, String> {
}
