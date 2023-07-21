package com.example.soonsul.liquor.dto;

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
@ApiModel(description = "전통주 필터링 정보")
public class LiquorFilteringDto {

    @ApiModelProperty(value = "나이", position = 1)
    private Integer age;

    @ApiModelProperty(value = "성별", position = 2)
    private String gender;
}
