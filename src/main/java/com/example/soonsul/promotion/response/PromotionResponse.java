package com.example.soonsul.promotion.response;


import com.example.soonsul.promotion.dto.PromotionDto;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;


@Getter
@ApiModel(description = "특정 프로모션 응답 모델")
public class PromotionResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final PromotionDto data;


    public PromotionResponse(ResultCode resultCode, PromotionDto data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static PromotionResponse of(ResultCode resultCode, PromotionDto data) {
        return new PromotionResponse(resultCode, data);
    }
}
