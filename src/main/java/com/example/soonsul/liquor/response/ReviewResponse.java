package com.example.soonsul.liquor.response;

import com.example.soonsul.liquor.dto.ReviewDto;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "리뷰 응답 모델")
public class ReviewResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final ReviewDto data;


    public ReviewResponse(ResultCode resultCode, ReviewDto data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static ReviewResponse of(ResultCode resultCode, ReviewDto data) {
        return new ReviewResponse(resultCode, data);
    }
}
