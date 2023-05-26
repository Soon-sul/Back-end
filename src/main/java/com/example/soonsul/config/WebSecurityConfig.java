package com.example.soonsul.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class WebSecurityConfig {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 접근에 대한 인증 설정
                .antMatchers("/login", "/signup", "/user").permitAll() // 누구나 접근 허용
                .antMatchers("/actuator/**").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .logout() // 로그아웃
                .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true) // 세션 날리기
        ;
    }

}
