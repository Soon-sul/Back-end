package com.example.soonsul.user.oauth.param;

import com.example.soonsul.user.oauth.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}