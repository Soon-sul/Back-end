package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.EvaluationNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationNumberRepository extends JpaRepository<EvaluationNumber, String> {
}
