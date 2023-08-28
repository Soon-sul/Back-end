package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="sale_place_info")
public class SalePlaceInfo {

    @Id
    @Column(name = "sale_place_info_id", nullable = false, unique = true)
    private String salePlaceInfoId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "site_url")
    private String siteUrl;
}
