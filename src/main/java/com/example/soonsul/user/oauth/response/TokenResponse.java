package com.example.soonsul.user.oauth.response;

import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.user.oauth.dto.TokenDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "토큰 응답 모델")
public class TokenResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private final int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private final String code;
    @ApiModelProperty(value = "응답 메세지")
    private final String message;
    @ApiModelProperty(value = "응답 데이터")
    private final TokenDto data;


    public TokenResponse(ResultCode resultCode, TokenDto data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static TokenResponse of(ResultCode resultCode, TokenDto data) {
        return new TokenResponse(resultCode, data);
    }

}
