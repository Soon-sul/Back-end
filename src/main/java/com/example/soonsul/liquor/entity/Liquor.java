package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="liquor")
public class Liquor {

    @Id
    @Column(name = "liquor_id", nullable = false, unique = true)
    private String liquorId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "sale_place")
    private String salePlace;

    @Column(name = "location")
    private String location;

    @Column(name = "ingredient")
    private String ingredient;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "lowest_price")
    private Long lowestPrice;

    @Column(name = "alcohol")
    private Double alcohol;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "region")
    private String region;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "liquor_catory")
    private String liquorCatory;

    @Column(name = "brewery")
    private String brewery;


    public void updateAverageRating(Double averageRating){
        this.averageRating= averageRating;
    }
}
