package com.example.soonsul.liquor.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BigCategory {
    LT("탁주"),
    LY("약주"),
    LC("청주"),
    LJ("증류식소주"),
    LG("과실주");

    private final String value;
}
