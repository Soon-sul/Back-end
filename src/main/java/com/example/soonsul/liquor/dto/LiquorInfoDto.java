package com.example.soonsul.liquor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
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

    @ApiModelProperty(value = "판매처 이름")
    private String salePlaceName;

    @ApiModelProperty(value = "판매처 전화번호")
    private String phoneNumber;

    @ApiModelProperty(value = "판매처 url")
    private String siteUrl;

    @ApiModelProperty(value = "소재지 리스트")
    private List<String> locationList;

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

    @ApiModelProperty(value = "위도")
    private Integer latitude;

    @ApiModelProperty(value = "경도")
    private Integer longitude;

    @ApiModelProperty(value = "도(지역) 코드값")
    private String region;

    @ApiModelProperty(value = "전통주 대표사진")
    private String imageUrl;

    @ApiModelProperty(value = "주종 코드값")
    private String liquorCatory;

    @ApiModelProperty(value = "양조장")
    private String brewery;

    @ApiModelProperty(value = "전통주 개인평점")
    private Double liquorPersonalRating;

    @ApiModelProperty(value = "평가수")
    private Long ratingNumber;

    @ApiModelProperty(value = "수상내역 리스트")
    private List<String> prizeList;
}
