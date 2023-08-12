package com.example.soonsul.main.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sort {
    STAR("star"),
    REVIEW("review"),
    LOWEST_COST("lowest-cost"),
    HIGHEST_COST("highest-cost");

    private final String value;
}
