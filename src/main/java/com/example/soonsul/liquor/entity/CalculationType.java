package com.example.soonsul.liquor.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CalculationType {
    ADD("add"),
    SUB("sub"),
    SUB_AND_ADD("sub_and_add");

    private final String value;
}
