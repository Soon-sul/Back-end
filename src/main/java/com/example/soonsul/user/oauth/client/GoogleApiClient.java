package com.example.soonsul.user.oauth.client;

import com.example.soonsul.user.oauth.OAuthProvider;
import com.example.soonsul.user.oauth.param.OAuthLoginParams;
import com.example.soonsul.user.oauth.response.GoogleInfoResponse;
import com.example.soonsul.user.oauth.response.OAuthInfoResponse;
import com.example.soonsul.user.oauth.token.GoogleTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleApiClient implements OAuthApiClient {
    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.google.token-uri}")
    private String tokenUri;

    @Value("${oauth.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.client-secret}")
    private String clientSecret;

    @Value("${oauth.google.resource-uri}")
    private String resourceUri;

    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.GOOGLE;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        GoogleTokens response = restTemplate.postForObject(tokenUri, request, GoogleTokens.class);
        assert response != null;

        return response.getAccessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.exchange(resourceUri, HttpMethod.GET, request, GoogleInfoResponse.class).getBody();
    }
}
