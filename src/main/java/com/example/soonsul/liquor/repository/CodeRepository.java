package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, String> {

}
