package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Click;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClickRepository extends CrudRepository<Click, String> {
    List<Click> findAllByLiquorId(String liquorId);
}
