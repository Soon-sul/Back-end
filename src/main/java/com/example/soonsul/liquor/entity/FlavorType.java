package com.example.soonsul.liquor.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FlavorType {
    SWEETNESS("sweetness"),
    ACIDITY("acidity"),
    CARBONIC_ACID("carbonicAcid"),
    HEAVY("heavy"),
    SCENT("scent"),
    DENSITY("density");

    private final String value;
}
