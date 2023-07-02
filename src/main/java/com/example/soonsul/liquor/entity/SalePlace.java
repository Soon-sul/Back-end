package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="sale_place")
public class SalePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_place_id", nullable = false, unique = true)
    private Long salePlaceId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "site_url")
    private String siteUrl;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="liquor_id")
    private Liquor liquor;
}
