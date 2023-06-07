package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.LiquorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorInfoRepository extends JpaRepository<LiquorInfo, String> {
}
