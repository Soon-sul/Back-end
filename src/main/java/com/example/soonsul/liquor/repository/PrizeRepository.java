package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {
    List<Prize> findAllByLiquor(Liquor liquor);
}
