package com.shin.noticeboard.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class webConfig implements WebMvcConfigurer {
    @Override
    // CORS - 동일 출처 정책(다른 출처의 자원에 접근을 맘대로 하면 보안적 문제)
    // addMapping - 허용할 매핑 주소의 패턴
    // alldowedOriginPatterns - CORS를 허용할 도메인 주소
    // allowCredentials - 쿠키허용
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://127.0.0.1:8080")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
