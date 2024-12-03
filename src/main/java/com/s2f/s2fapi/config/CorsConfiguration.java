package com.s2f.s2fapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class CorsConfiguration implements WebMvcConfigurer {
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info(
                String.format(
                        "[%s][%s] => check my cors config",
                        this.getClass(), "corsConfigurationSource"));
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("Authorization", "content-type", "x-auth-token")
                .allowCredentials(true)
                .exposedHeaders("x-auth-token", "X-Get-Header")
                .maxAge(3600L);
    }
}
