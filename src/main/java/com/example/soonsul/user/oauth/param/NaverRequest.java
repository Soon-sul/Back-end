package com.example.soonsul.user.oauth.param;

import com.example.soonsul.user.oauth.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Builder
@AllArgsConstructor
public class NaverRequest implements OAuthLoginParams{
    private String id;
    private String name;
    private String image;
    private String email;

    @Override
    public OAuthProvider oAuthProvider(){
        return OAuthProvider.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", name);
        return body;
    }
}
