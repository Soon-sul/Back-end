package com.example.soonsul.scan;

import com.example.soonsul.liquor.entity.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Long> {

    @Query(nativeQuery = true,
            value="SELECT * FROM scan s WHERE s.user_id = :userId" +
                    " ORDER BY s.scan_id DESC")
    Page<Scan> findAllByUser(Pageable pageable, @Param("userId") String userId);
}
