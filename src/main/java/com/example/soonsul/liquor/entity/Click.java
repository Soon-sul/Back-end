package com.example.soonsul.liquor.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@RedisHash(value = "click")
public class Click {

    @Id
    private String id;

    @Indexed
    private String liquorId;

    @Indexed
    private Long userId;

    @TimeToLive
    private long ttl;
}
