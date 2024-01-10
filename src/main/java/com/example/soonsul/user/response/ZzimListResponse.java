package com.example.soonsul.user.response;

import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.user.dto.ZzimDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "찜 리스트 응답 모델")
public class ZzimListResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final List<ZzimDto> data;


    public ZzimListResponse(ResultCode resultCode, List<ZzimDto> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static ZzimListResponse of(ResultCode resultCode, List<ZzimDto> data) {
        return new ZzimListResponse(resultCode, data);
    }
}
