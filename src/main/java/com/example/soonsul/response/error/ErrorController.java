package com.example.soonsul.response.error;

import com.example.soonsul.user.exception.AccessTokenExpired;
import com.example.soonsul.user.exception.AccessTokenNotSame;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorController {

    @GetMapping("/api/error/1")
    public void accessTokenExpiredError()  {
        throw new AccessTokenExpired("access token expired", ErrorCode.ACCESS_TOKEN_EXPIRED);
    }

    @GetMapping("/api/error/2")
    public void accessTokenNotSameError()  {
        throw new AccessTokenNotSame("access token not same", ErrorCode.ACCESS_TOKEN_NOT_SAME);
    }

}
