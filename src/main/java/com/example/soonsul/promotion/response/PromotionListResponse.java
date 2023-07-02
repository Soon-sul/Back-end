package com.example.soonsul.promotion.response;

import com.example.soonsul.promotion.dto.PromotionDto;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "프로모션 리스트 응답 모델")
public class PromotionListResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final List<PromotionDto> data;


    public PromotionListResponse(ResultCode resultCode, List<PromotionDto> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static PromotionListResponse of(ResultCode resultCode, List<PromotionDto> data) {
        return new PromotionListResponse(resultCode, data);
    }
}
