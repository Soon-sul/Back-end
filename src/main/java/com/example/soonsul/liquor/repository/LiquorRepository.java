package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Liquor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiquorRepository extends JpaRepository<Liquor, String> {
    Optional<Liquor> findByName(String name);
}
