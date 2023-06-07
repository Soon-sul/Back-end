package com.example.soonsul.user.oauth.response;

import com.example.soonsul.user.oauth.OAuthProvider;

public interface OAuthInfoResponse {
    String getId();
    OAuthProvider getOAuthProvider();
}