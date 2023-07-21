package com.example.soonsul.liquor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@RedisHash(value = "filtering_click", timeToLive = 2678400)
public class FilteringClick {

    @Id
    private String id;

    @Indexed
    private Integer age;

    @Indexed
    private String gender;

    private String liquorId;
}
