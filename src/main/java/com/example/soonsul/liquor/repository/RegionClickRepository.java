package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.RegionClick;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RegionClickRepository extends CrudRepository<RegionClick, String> {
    List<RegionClick> findAllByRegion(String region);
}
