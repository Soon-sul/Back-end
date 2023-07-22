package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Scrap;
import com.example.soonsul.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    void deleteByUserAndLiquor(User user, Liquor liquor);
    boolean existsByUserAndLiquor(User user, Liquor liquor);
    Optional<Scrap> findByUserAndLiquor(User user, Liquor liquor);
}
