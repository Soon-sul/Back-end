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

    @ApiModelProperty(value = "전통주 이름", position = 1)
    private String name;

    @ApiModelProperty(value = "성분", position = 2)
    private String ingredient;

    @ApiModelProperty(value = "평균 평점", position = 3)
    private Double averageRating;

    @ApiModelProperty(value = "최저가", position = 4)
    private Long lowestPrice;

    @ApiModelProperty(value = "도수", position = 5)
    private Double alcohol;

    @ApiModelProperty(value = "용량", position = 6)
    private Integer capacity;

    @ApiModelProperty(value = "지역 이름", position = 7)
    private String region;

    @ApiModelProperty(value = "전통주 대표사진", position = 8)
    private String imageUrl;

    @ApiModelProperty(value = "주종 카테고리", position = 9)
    private String liquorCategory;

    @ApiModelProperty(value = "전통주 개인평점", position = 10)
    private Double liquorPersonalRating;

    @ApiModelProperty(value = "리뷰 평가수", position = 11)
    private Integer ratingNumber;

    @ApiModelProperty(value = "전통주 필터링 정보 ex)20대 여성이 가장 좋아하는 술", position = 12)
    private List<LiquorFilteringDto> filtering;

}
