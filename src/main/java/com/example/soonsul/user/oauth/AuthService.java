package com.example.soonsul.user.oauth;

import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.oauth.jwt.JwtTokenProvider;
import com.example.soonsul.user.User;
import com.example.soonsul.user.UserRepository;
import com.example.soonsul.user.exception.RefreshTokenExpiredException;
import com.example.soonsul.user.exception.UserNotExistException;
import com.example.soonsul.user.oauth.dto.SignupDto;
import com.example.soonsul.user.oauth.dto.TokenDto;
import com.example.soonsul.user.oauth.param.OAuthLoginParams;
import com.example.soonsul.user.redis.RefreshToken;
import com.example.soonsul.user.redis.RefreshTokenRepository;
import com.example.soonsul.user.oauth.response.OAuthInfoResponse;
import com.example.soonsul.util.RandomNickName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public TokenDto login(OAuthLoginParams params) {
        final OAuthInfoResponse response = requestOAuthInfoService.request(params);
        final String oauthId= response.getId();
        final OAuthProvider oauthProvider= params.oAuthProvider();
        final Optional<User> user= userRepository.findByOauthId(oauthId);

        if(user.isPresent()) {          //이미 가입된 사용자
            final RefreshToken refreshToken= jwtTokenProvider.generateJwtRefreshToken(user.get());
            refreshTokenRepository.save(refreshToken);
            return TokenDto.builder()
                    .accessToken(jwtTokenProvider.generateJwtToken(user.get()))
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }
        else{                          //신규가입자
            return TokenDto.builder()
                    .oauthId(oauthId)
                    .oauthProvider(oauthProvider)
                    .build();
        }
    }


    @Transactional
    public TokenDto signup(SignupDto signupDto){
        final User user= User.builder()
                .nickname(RandomNickName.generate().toString())
                .profileImage(null)
                .phoneNumber(null)
                .gender(signupDto.getGender())
                .age(signupDto.getAge())
                .flagAge(signupDto.isFlagAge())
                .flagTerms(signupDto.isFlagTerms())
                .flagPrivacy(signupDto.isFlagPrivacy())
                .flagWithdrawal(false)
                .oauthId(signupDto.getOauthId())
                .oAuthProvider(OAuthProvider.valueOf(signupDto.getOauthProvider()))
                .build();
        userRepository.save(user);

        return TokenDto.builder()
                .accessToken(jwtTokenProvider.generateJwtToken(user))
                .refreshToken(jwtTokenProvider.generateJwtRefreshToken(user).getRefreshToken())
                .build();
    }

    //New Access Token 재발급
    @Transactional
    public String refreshToken(String refreshToken) {
        Optional<RefreshToken> getRefreshToken= refreshTokenRepository.findById(refreshToken);
        if(getRefreshToken.isPresent()){
            User user= userRepository.findById(getRefreshToken.get().getUserId())
                    .orElseThrow(()-> new UserNotExistException("user not exist", ErrorCode.USER_NOT_EXIST));
            return jwtTokenProvider.generateJwtToken(user);
        }
        else{
            throw new RefreshTokenExpiredException("refresh token expired", ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }


    //액세스토큰 유효성 검사
    @Transactional(readOnly = true)
    public boolean isValidToken(String token) {
        return jwtTokenProvider.isValidToken(token);
    }
}