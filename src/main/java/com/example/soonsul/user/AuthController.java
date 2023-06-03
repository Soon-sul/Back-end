package com.example.soonsul.user;

import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import com.example.soonsul.user.exception.OAuthLoginException;
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
    private final OAuthLoginService oAuthLoginService;


    @ApiOperation(value = "카카오 로그인 리다이렉션 api")
    @GetMapping("/kakao/callback")
    public ResponseEntity<ResultResponse> getCodeFromKakao(@ModelAttribute KakaoParams kakaoParams) {
        if(kakaoParams.getError()!=null) throw new OAuthLoginException("OAuth login fail", ErrorCode.OAUTH_LOGIN_FAIL);

        TokenDto data= oAuthLoginService.login(kakaoParams);
        if(data.getRefreshToken()==null){
            return ResponseEntity.ok(ResultResponse.of(ResultCode.NEW_USER_LOGIN_SUCCESS, data));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ORIGINAL_USER_LOGIN_SUCCESS, data));
    }


    @ApiOperation(value = "네이버 로그인 리다이렉션 api")
    @GetMapping("/naver/callback")
    public ResponseEntity<ResultResponse> loginNaver(@ModelAttribute NaverParams naverParams) {
        if(naverParams.getError()!=null) throw new OAuthLoginException("OAuth login fail", ErrorCode.OAUTH_LOGIN_FAIL);

        TokenDto data= oAuthLoginService.login(naverParams);
        if(data.getRefreshToken()==null){
            return ResponseEntity.ok(ResultResponse.of(ResultCode.NEW_USER_LOGIN_SUCCESS, data));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ORIGINAL_USER_LOGIN_SUCCESS, data));
    }

    @ApiOperation(value = "구글 로그인 리다이렉션 api")
    @GetMapping("/google/callback")
    public ResponseEntity<ResultResponse> loginGoogle(@ModelAttribute GoogleParams googleParams) {
        if(googleParams.getError()!=null) throw new OAuthLoginException("OAuth login fail", ErrorCode.OAUTH_LOGIN_FAIL);

        TokenDto data= oAuthLoginService.login(googleParams);
        if(data.getRefreshToken()==null){
            return ResponseEntity.ok(ResultResponse.of(ResultCode.NEW_USER_LOGIN_SUCCESS, data));
        }
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ORIGINAL_USER_LOGIN_SUCCESS, data));
    }


    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup(@RequestBody SignupDto signupDto) {
        TokenDto data= oAuthLoginService.signup(signupDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ORIGINAL_USER_LOGIN_SUCCESS, data));
    }


    @ApiOperation(value = "새로운 Access Token 재발급")
    @PostMapping(value = "/refresh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "AuthorizationAccess", value = "AuthorizationAccess", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "AuthorizationRefresh", value = "AuthorizationRefresh", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<ResultResponse> refreshToken(
            @RequestHeader(value="AuthorizationAccess") String token,
            @RequestHeader(value="AuthorizationRefresh") String refreshToken
    ) {
        String newAccessToken= oAuthLoginService.refreshToken(refreshToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add(AuthConstants.AUTH_HEADER_ACCESS, newAccessToken);
        return ResponseEntity.ok().headers(headers).body(ResultResponse.of(ResultCode.GENERATE_ACCESS_TOKEN_SUCCESS));
    }
}