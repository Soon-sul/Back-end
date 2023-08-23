package com.example.soonsul.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "유저 프로필 정보 (닉네임, 프로필 사진)")
public class UserProfileDto {

    @ApiModelProperty(value = "유저 닉네임", position = 1)
    private String nickname;

    @ApiModelProperty(value = "유저 프로필 사진", position = 2)
    private String profileImage;
}
