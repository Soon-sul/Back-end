package com.example.soonsul.promotion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "프로모션 정보")
public class PromotionDto {

    @ApiModelProperty(value = "프로모션 pk", position = 1)
    private Long promotionId;

    @ApiModelProperty(value = "프로모션 제목", position = 2)
    private String title;

    @ApiModelProperty(value = "프로모션 내용", position = 3)
    private String content;

    @ApiModelProperty(value = "프로모션 시작 날짜", position = 4)
    private LocalDate beginDate;

    @ApiModelProperty(value = "프로모션 종료 날짜", position = 5)
    private LocalDate endDate;

    @ApiModelProperty(value = "프로모션 위치", position = 6)
    private String location;

    @ApiModelProperty(value = "프로모션 이미지", position = 7)
    private String image;

    @ApiModelProperty(value = "찜 여부", position = 8)
    private boolean flagZzim;

    @ApiModelProperty(value = "프로모션 관련된 전통주 리스트 (pk)", position = 9)
    private List<String> liquorList;
}
