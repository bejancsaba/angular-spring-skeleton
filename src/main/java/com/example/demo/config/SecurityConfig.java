package com.example.demo.config;

import com.example.demo.security.JwtBuilderService;
import com.example.demo.security.JwtValidatorService;
import com.example.demo.security.UnsuccessfulJwtAuthenticationFailureHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtValidatorService jwtValidatorService() {
        return new JwtValidatorService();
    }

    @Bean
    public JwtBuilderService jwtBuilderService() {
        return new JwtBuilderService();
    }

    @Bean
    public UnsuccessfulJwtAuthenticationFailureHandler unsuccessfulJwtAuthenticationFailureHandler(
            ObjectMapper objectMapper) {
        return new UnsuccessfulJwtAuthenticationFailureHandler(objectMapper);
    }
}
