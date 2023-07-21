package com.example.soonsul.config.s3;

import com.example.soonsul.response.error.ErrorCode;
import lombok.Getter;

@Getter
public class FileUpload extends RuntimeException{
    private final ErrorCode errorCode;

    public FileUpload(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
