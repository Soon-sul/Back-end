package com.example.soonsul.user.oauth.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {
    public static final String AUTH_HEADER_REFRESH = "refreshToken";
    public static final String AUTH_HEADER_ACCESS = "Authorization";
}
