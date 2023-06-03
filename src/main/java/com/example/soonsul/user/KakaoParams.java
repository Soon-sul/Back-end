package com.example.soonsul.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Builder
@AllArgsConstructor
public class KakaoParams implements OAuthLoginParams {
    private String code;
    private String state;
    private String error;
    private String error_description;


    @Override
    public OAuthProvider oAuthProvider(){
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        return body;
    }
}
