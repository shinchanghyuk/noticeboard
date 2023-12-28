package com.shin.noticeboard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shin.noticeboard.utils.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Value("${app.cors.baseurl}")
    private String baseUrl;

    private final ObjectMapper objectMapper;

    @Autowired
    public webConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    // CORS - 동일 출처 정책(다른 출처의 자원에 접근을 맘대로 하면 보안적 문제)
    // addMapping - 허용할 매핑 주소의 패턴
    // alldowedOriginPatterns - CORS를 허용할 도메인 주소
    // allowCredentials - 쿠키허용
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(baseUrl)
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(objectMapper))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/index.html", "META-INF/resource/*", "/login", "/register",
                        "/css/**", "/js/**", "/*.ico", "/error"
                );
    }
}
