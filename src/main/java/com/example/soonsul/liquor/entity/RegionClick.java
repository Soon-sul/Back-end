package com.example.soonsul.liquor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@RedisHash(value = "region_click", timeToLive = 86400)
public class RegionClick {

    @Id
    private String id;

    @Indexed
    private String region;      //지역코드

    private String liquorId;

    private Double latitude;

    private Double longitude;

}
