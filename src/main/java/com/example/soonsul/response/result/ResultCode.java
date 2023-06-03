package com.example.soonsul.response.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // user
    NEW_USER_LOGIN_SUCCESS(201, "U001", "신규 유저 로그인 완료했습니다."),
    ORIGINAL_USER_LOGIN_SUCCESS(200, "U002", "기존 유저 로그인 완료했습니다."),
    GENERATE_ACCESS_TOKEN_SUCCESS(200,"U003","새로운 Access Token을 발급했습니다.");


    private final int status;
    private final String code;
    private final String message;
}