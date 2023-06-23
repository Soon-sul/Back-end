package com.example.soonsul.liquor.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "개인 맛평가 정보")
public class PersonEvaluationDto {

    private Integer sweetness;
    private Integer acidity;
    private Integer carbonicAcid;
    private Integer heavy;
    private Integer scent;
    private Integer density;
}
