package com.example.soonsul.promotion.exception;

import com.example.soonsul.response.error.ErrorCode;
import lombok.Getter;

@Getter
public class PromotionNotExist extends RuntimeException {

    private final ErrorCode errorCode;

    public PromotionNotExist(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
