package com.example.soonsul.response.error;

import com.example.soonsul.user.exception.AccessTokenExpiredException;
import com.example.soonsul.user.exception.AccessTokenNotSameException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorController {

    @RequestMapping("/api/error/1")
    public void accessTokenExpiredError()  {
        throw new AccessTokenExpiredException("access token expired", ErrorCode.ACCESS_TOKEN_EXPIRED);
    }

    @RequestMapping("/api/error/2")
    public void accessTokenNotSameError()  {
        throw new AccessTokenNotSameException("access token not same", ErrorCode.ACCESS_TOKEN_NOT_SAME);
    }

}
