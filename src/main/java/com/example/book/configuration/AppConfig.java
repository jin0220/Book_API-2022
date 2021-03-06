package com.example.book.configuration;

import com.example.book.interceptor.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 생성한 인터셉터 등록
 * 컨트롤러에 도달하기 전에 등록한 인터셉터를 먼저 거치게 됨.
 * */
@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {
    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
            .excludePathPatterns("/api/v1/signin", "/api/v1/signup", "/api/v1/refresh"); // 로그인, 회원가입, 토큰 재발급은 인터셉트에서 제외
    }
}
