package com.example.soonsul.main.repository;

import com.example.soonsul.main.entity.BannerLiquor;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerLiquorRepository extends JpaRepository<BannerLiquor, Long> {

    @Query(nativeQuery = true,
            value="SELECT b.liquor_id FROM banner_liquor b WHERE b.main_banner_id = :mainBannerId")
    List<String> findByMainBanner(@Param("mainBannerId") Long mainBannerId);
}
