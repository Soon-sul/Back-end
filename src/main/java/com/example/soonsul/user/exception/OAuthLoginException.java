package com.example.soonsul.user.exception;

import com.example.soonsul.response.error.ErrorCode;
import lombok.Getter;

@Getter
public class OAuthLoginException extends RuntimeException{

    private final ErrorCode errorCode;

    public OAuthLoginException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
