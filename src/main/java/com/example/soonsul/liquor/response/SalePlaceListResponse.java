package com.example.soonsul.liquor.response;

import com.example.soonsul.liquor.dto.SalePlaceListDto;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "전통주 판매처 응답 모델")
public class SalePlaceListResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final List<SalePlaceListDto> data;


    public SalePlaceListResponse(ResultCode resultCode, List<SalePlaceListDto> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static SalePlaceListResponse of(ResultCode resultCode, List<SalePlaceListDto> data) {
        return new SalePlaceListResponse(resultCode, data);
    }
}
