package com.example.soonsul.user.oauth;

import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import com.example.soonsul.user.oauth.dto.SignupDto;
import com.example.soonsul.user.oauth.dto.TokenDto;
import com.example.soonsul.user.exception.OAuthLoginException;
import com.example.soonsul.user.oauth.jwt.AuthConstants;
import com.example.soonsul.user.oauth.param.GoogleParams;
import com.example.soonsul.user.oauth.param.KakaoParams;
import com.example.soonsul.user.oauth.param.NaverParams;
import com.example.soonsul.user.oauth.response.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="User")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "카카오 로그인")
    @GetMapping("/login/kakao")
    public ResponseEntity<TokenResponse> loginKakao(@ModelAttribute KakaoParams kakaoParams) {
        //if(kakaoParams.getError()!=null) throw new OAuthLoginException("OAuth login fail", ErrorCode.OAUTH_LOGIN_FAIL);

        TokenDto data= authService.login(kakaoParams);
        if(data.getRefreshToken()==null){
            return ResponseEntity.ok(TokenResponse.of(ResultCode.NEW_USER_LOGIN_SUCCESS, data));
        }
        return ResponseEntity.ok(TokenResponse.of(ResultCode.ORIGINAL_USER_LOGIN_SUCCESS, data));
    }


    @ApiOperation(value = "네이버 로그인")
    @GetMapping("/login/naver")
    public ResponseEntity<TokenResponse> loginNaver(@ModelAttribute NaverParams naverParams) {
        //if(naverParams.getError()!=null) throw new OAuthLoginException("OAuth login fail", ErrorCode.OAUTH_LOGIN_FAIL);

        TokenDto data= authService.login(naverParams);
        if(data.getRefreshToken()==null){
            return ResponseEntity.ok(TokenResponse.of(ResultCode.NEW_USER_LOGIN_SUCCESS, data));
        }
        return ResponseEntity.ok(TokenResponse.of(ResultCode.ORIGINAL_USER_LOGIN_SUCCESS, data));
    }

    @ApiOperation(value = "구글 로그인")
    @GetMapping("/login/google")
    public ResponseEntity<TokenResponse> loginGoogle(@ModelAttribute GoogleParams googleParams) {
        //if(googleParams.getError()!=null) throw new OAuthLoginException("OAuth login fail", ErrorCode.OAUTH_LOGIN_FAIL);

        TokenDto data= authService.login(googleParams);
        if(data.getRefreshToken()==null){
            return ResponseEntity.ok(TokenResponse.of(ResultCode.NEW_USER_LOGIN_SUCCESS, data));
        }
        return ResponseEntity.ok(TokenResponse.of(ResultCode.ORIGINAL_USER_LOGIN_SUCCESS, data));
    }


    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signup(@RequestBody SignupDto signupDto) {
        TokenDto data= authService.signup(signupDto);
        return ResponseEntity.ok(TokenResponse.of(ResultCode.SIGNUP_SUCCESS, data));
    }


    @ApiOperation(value = "새로운 Access Token 재발급")
    @PostMapping(value = "/refresh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "refreshToken", value = "refreshToken", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<ResultResponse> refreshToken(
            @RequestHeader(value="Authorization") String token,
            @RequestHeader(value="refreshToken") String refreshToken
    ) {
        String newAccessToken= authService.refreshToken(refreshToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add(AuthConstants.AUTH_HEADER_ACCESS, newAccessToken);
        return ResponseEntity.ok().headers(headers).body(ResultResponse.of(ResultCode.GENERATE_ACCESS_TOKEN_SUCCESS));
    }


    @ApiOperation(value = "액세스토큰 유효성 검사")
    @GetMapping("/token")
    public ResponseEntity<ResultResponse> isValidToken(@RequestHeader(value="AuthorizationAccess") String token) {
        boolean data= authService.isValidToken(token);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.TOKEN_VALID_CHECK_SUCCESS, data));
    }

}