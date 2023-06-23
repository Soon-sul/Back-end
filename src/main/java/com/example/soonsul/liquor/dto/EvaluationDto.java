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
@ApiModel(description = "전체 평균 맛평가 정보")
public class EvaluationDto {

    @ApiModelProperty(value = "단맛")
    private Double sweetness;

    @ApiModelProperty(value = "산미")
    private Double acidity;

    @ApiModelProperty(value = "탄산")
    private Double carbonicAcid;

    @ApiModelProperty(value = "묵직함")
    private Double heavy;

    @ApiModelProperty(value = "향")
    private Double scent;

    @ApiModelProperty(value = "꾸덕함")
    private Double density;
}
