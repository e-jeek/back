package com.ejeek.back.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class securityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/auth/**")
                                ).authenticated()
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/h2-console/**")
                                ).permitAll()
                )
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스 spring security 대상에서 제외
        return (web) ->
                web
                        .ignoring()
                        .requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations()
                        );
    }
}
