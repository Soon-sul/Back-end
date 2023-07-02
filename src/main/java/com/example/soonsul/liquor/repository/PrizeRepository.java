package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Prize;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {

    @Query(nativeQuery = true,
            value="SELECT p.prize_name FROM prize p WHERE p.liquor_id = :liquorId")
    List<String> findAllByLiquor(@Param("liquorId") String liquorId);
}