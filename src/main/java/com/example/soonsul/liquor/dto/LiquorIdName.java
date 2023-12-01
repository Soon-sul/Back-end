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
@ApiModel(description = "전통주 id와 이름 정보")
public class LiquorIdName {

    @ApiModelProperty(value = "전통주 id", position = 1)
    private String liquorId;

    @ApiModelProperty(value = "전통주 이름", position = 2)
    private String name;
}
