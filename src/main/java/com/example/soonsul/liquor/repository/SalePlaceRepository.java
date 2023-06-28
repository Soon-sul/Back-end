package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.SalePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalePlaceRepository extends JpaRepository<SalePlace, Long> {
}
