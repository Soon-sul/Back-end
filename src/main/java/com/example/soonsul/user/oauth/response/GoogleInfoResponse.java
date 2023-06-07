package com.example.soonsul.user.oauth.response;

import com.example.soonsul.user.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoResponse implements OAuthInfoResponse {

    @JsonProperty("id")
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }
}
