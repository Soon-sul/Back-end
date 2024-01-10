package com.example.soonsul.main.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="banner_liquor")
public class BannerLiquor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_liquor_id", nullable = false, unique = true)
    private Long bannerLiquorId;

    @Column(name = "liquor_id")
    private String liquorId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="main_banner_id")
    private MainBanner mainBanner;
}
