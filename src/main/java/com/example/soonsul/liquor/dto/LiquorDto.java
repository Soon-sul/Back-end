package com.example.soonsul.liquor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiquorDto {
    private String name;
    private String salePlace;
    private String location;
    private String ingredient;
    private Double averageRating;
    private Long lowestPrice;
    private Double alcohol;
    private Integer capacity;
    private Long viewCount;
    private Integer latitude;
    private Integer longitude;
    private String region;
    private String imageUrl;
    private String liquorCatory;
    private String brewery;
    private Double liquorPersonalRating;
    private Long ratingNumber;
}
