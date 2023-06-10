package com.example.soonsul.user.oauth.dto;

import com.example.soonsul.user.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String oauthId;
    private OAuthProvider oAuthProvider;
}
