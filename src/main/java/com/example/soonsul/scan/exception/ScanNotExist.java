package com.example.soonsul.scan.exception;

import com.example.soonsul.response.error.ErrorCode;
import lombok.Getter;

@Getter
public class ScanNotExist extends RuntimeException {

    private final ErrorCode errorCode;

    public ScanNotExist(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
