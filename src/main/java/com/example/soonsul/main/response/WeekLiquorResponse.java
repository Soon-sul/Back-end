package com.example.soonsul.main.response;

import com.example.soonsul.liquor.dto.LiquorInfoDto;
import com.example.soonsul.liquor.response.LiquorInfoResponse;
import com.example.soonsul.main.dto.WeekLiquorDto;
import com.example.soonsul.response.result.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "전통주 리스트 응답 모델")
public class WeekLiquorResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final List<WeekLiquorDto> data;


    public WeekLiquorResponse(ResultCode resultCode, List<WeekLiquorDto> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static WeekLiquorResponse of(ResultCode resultCode, List<WeekLiquorDto> data) {
        return new WeekLiquorResponse(resultCode, data);
    }
}
