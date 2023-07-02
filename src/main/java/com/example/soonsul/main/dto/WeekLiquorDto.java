package com.example.soonsul.main.dto;

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
@ApiModel(description = "이번주 전통주 정보")
public class WeekLiquorDto {

    @ApiModelProperty(value = "전통주 pk", position = 1)
    private String liquorId;

    @ApiModelProperty(value = "전통주 대표 사진", position = 2)
    private String imageUrl;
}
