package com.example.soonsul.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //global
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),

    //auth
    ACCESS_TOKEN_EXPIRED(401,"A001","ACCESS TOKEN EXPIRED"),
    ACCESS_TOKEN_NOT_SAME(401,"A002","ACCESS TOKEN NOT SAME"),
    REFRESH_TOKEN_EXPIRED(401,"A003","REFRESH TOKEN EXPIRED"),
    TOKEN_NULL(401,"A004","TOKEN NULL"),
    OAUTH_LOGIN_FAIL(500,"A005","OAUTH LOGIN FAIL"),

    //user
    USER_NOT_EXIST(500,"U001","LOGIN USER NOT EXIST"),

    //liquor
    LIQUOR_NOT_EXIST(500,"L001","LIQUOR NOT EXIST");

    final private int status;
    final private String errorCode;
    final private String message;
}
