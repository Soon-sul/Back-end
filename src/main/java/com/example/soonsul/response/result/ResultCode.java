package com.example.soonsul.response.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // user
    NEW_USER_LOGIN_SUCCESS(200, "A001", "신규 유저 로그인 완료했습니다."),
    ORIGINAL_USER_LOGIN_SUCCESS(200, "A002", "기존 유저 로그인 완료했습니다."),
    SIGNUP_SUCCESS(201,"A003","회원가입 완료했습니다."),
    GENERATE_ACCESS_TOKEN_SUCCESS(200,"A004","새로운 Access Token을 발급했습니다."),
    TOKEN_VALID_CHECK_SUCCESS(200,"A005","Access Token 유효성 검사 완료했습니다."),

    //scan
    GET_SCANNED_LIQUOR_NAME_SUCCESS(200,"S001","스캔한 제품의 주류명을 반환했습니다.");


    private final int status;
    private final String code;
    private final String message;
}