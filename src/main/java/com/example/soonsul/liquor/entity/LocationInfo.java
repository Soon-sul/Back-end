package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="location_info")
public class LocationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_info_id", nullable = false, unique = true)
    private Long locationInfoId;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "brewery")
    private String brewery;


    public void updateLatitude(Double latitude){
        this.latitude= latitude;
    }

    public void updateLongitude(Double longitude){
        this.longitude= longitude;
    }
}
