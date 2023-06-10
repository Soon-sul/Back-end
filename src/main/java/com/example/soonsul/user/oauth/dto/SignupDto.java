package com.example.soonsul.user.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupDto {
    private String oauthId;
    private String oAuthProvider;
    private String gender;
    private Integer age;
    private boolean flagAge;
    private boolean flagTerms;
    private boolean flagPrivacy;
}
