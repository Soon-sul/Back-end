package com.example.soonsul.user.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RequiredArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 7776000)
public class RefreshToken {

    @Id
    private final String refreshToken;

    private final String userId;

}