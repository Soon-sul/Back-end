package com.example.soonsul.user.oauth.dto;

import com.example.soonsul.user.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "토큰 정보")
public class TokenDto {

    @ApiModelProperty(value = "액세스 토큰 (기존 유저일 경우)", position = 1)
    private String accessToken;

    @ApiModelProperty(value = "리프레쉬 토큰 (기존 유저일 경우)", position = 2)
    private String refreshToken;

    @ApiModelProperty(value = "oauth id (새로운 유저일 경우)", position = 3)
    private String oauthId;

    @ApiModelProperty(value = "oauth를 제공하는 플랫폼 (새로운 유저일 경우)", position = 4)
    private OAuthProvider oauthProvider;
}
