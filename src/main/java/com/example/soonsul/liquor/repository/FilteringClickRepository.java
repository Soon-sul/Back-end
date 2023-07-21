package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.FilteringClick;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilteringClickRepository extends CrudRepository<FilteringClick, String> {
    List<FilteringClick> findAllByAgeAndGender(Integer age, String gender);
}
