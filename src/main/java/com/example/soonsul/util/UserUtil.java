package com.example.soonsul.util;

import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.exception.UserNotExist;
import com.example.soonsul.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {
    private final UserRepository userRepository;

    public User getUserByAuthentication(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        try {
            return userRepository.findById(authentication.getName())
                    .orElseThrow(() -> new UserNotExist("login user not exist", ErrorCode.USER_NOT_EXIST));
        } catch (Exception e) {
            log.error(authentication.getName());
            throw new RuntimeException("");
        }


    }

    public User getUserById(String userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotExist("login user not exist", ErrorCode.USER_NOT_EXIST));
    }
}
