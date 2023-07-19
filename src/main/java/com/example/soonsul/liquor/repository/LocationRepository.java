package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Location;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(nativeQuery = true,
            value="SELECT l.name FROM location l WHERE l.liquor_id = :liquorId")
    List<String> findAllByLiquor(@Param("liquorId") String liquorId);
    List<Location> findAllByLiquor(Liquor liquor);
}
