package com.example.soonsul.liquor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "전통주 상세 정보")
public class LiquorInfoDto {

    @ApiModelProperty(value = "전통주 이름")
    private String name;

    @ApiModelProperty(value = "성분")
    private String ingredient;

    @ApiModelProperty(value = "평균 평점")
    private Double averageRating;

    @ApiModelProperty(value = "최저가")
    private Long lowestPrice;

    @ApiModelProperty(value = "도수")
    private Double alcohol;

    @ApiModelProperty(value = "용량")
    private Integer capacity;

    @ApiModelProperty(value = "조회수")
    private Long viewCount;

    @ApiModelProperty(value = "지역 이름")
    private String region;

    @ApiModelProperty(value = "전통주 대표사진")
    private String imageUrl;

    @ApiModelProperty(value = "주종 카테고리")
    private String liquorCategory;

    @ApiModelProperty(value = "전통주 개인평점")
    private Double liquorPersonalRating;

    @ApiModelProperty(value = "평가수")
    private Long ratingNumber;

}
