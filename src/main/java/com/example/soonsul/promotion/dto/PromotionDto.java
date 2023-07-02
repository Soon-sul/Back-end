package com.example.soonsul.promotion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "프로모션 정보")
public class PromotionDto {

    @ApiModelProperty(value = "프로모션 pk", position = 1)
    private Long promotionId;

    @ApiModelProperty(value = "제목", position = 2)
    private String title;

    @ApiModelProperty(value = "내용", position = 3)
    private String content;

    @ApiModelProperty(value = "시작 날짜", position = 4)
    private LocalDate beginDate;

    @ApiModelProperty(value = "종료 날짜", position = 5)
    private LocalDate endDate;

    @ApiModelProperty(value = "위치", position = 6)
    private String location;

    @ApiModelProperty(value = "메인 이미지 url", position = 7)
    private String image;
}
