package com.example.soonsul.scan.response;

import com.example.soonsul.main.dto.RegionLiquorDto;
import com.example.soonsul.main.response.RegionLiquorResponse;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.scan.dto.ScanDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(description = "사진 히스토리 리스트 응답 모델")
public class ScanResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final List<ScanDto> data;


    public ScanResponse(ResultCode resultCode, List<ScanDto> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static ScanResponse of(ResultCode resultCode, List<ScanDto> data) {
        return new ScanResponse(resultCode, data);
    }
}
