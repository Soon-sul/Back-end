package com.example.soonsul.user.exception;

import com.example.soonsul.response.error.ErrorCode;
import lombok.Getter;

@Getter
public class AccessTokenNotSameException extends RuntimeException{
    private final ErrorCode errorCode;

    public AccessTokenNotSameException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
