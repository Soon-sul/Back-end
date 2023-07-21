package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.LiquorFiltering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiquorFilteringRepository extends JpaRepository<LiquorFiltering, Long> {
    List<LiquorFiltering> findAllByLiquorId(String liquorId);
}
