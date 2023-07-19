package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.LocationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long> {
}
