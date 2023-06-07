package com.example.soonsul.liquor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiquorInfoDto {
    private String name;
    private String title;
    private String salePlace;
    private String location;
    private String ingredient;
    private Double averageRating;
    private Long lowestPrice;
    private String alcohol;
    private String capacity;
    private Long viewCount;
    private Integer latitude;
    private Integer longitude;
    private String region;
    private String imageUrl;
}
