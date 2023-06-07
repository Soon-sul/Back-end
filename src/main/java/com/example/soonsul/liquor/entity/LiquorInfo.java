package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="liquor_info")
public class LiquorInfo {

    @Id
    @Column(name = "liquor_info_id", nullable = false, unique = true)
    private String liquorInfoId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sale_place", nullable = false)
    private String salePlace;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "ingredient", nullable = false)
    private String ingredient;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "lowest_price")
    private Long lowestPrice;

    @Column(name = "alcohol", nullable = false)
    private String alcohol;

    @Column(name = "capacity", nullable = false)
    private String capacity;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "latitude", nullable = false)
    private Integer latitude;

    @Column(name = "longitude", nullable = false)
    private Integer longitude;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
