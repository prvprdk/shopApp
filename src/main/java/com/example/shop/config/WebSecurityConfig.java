package com.example.shop.config;

import com.example.shop.repository.UserRepository;
import com.example.shop.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    private UserRepository userRepository;
    @Autowired
    private CustomUserService customUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/", "/login", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(auth -> auth.userInfoEndpoint()
                        .oidcUserService(customUserService))
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll())
                .csrf().disable();

        return http.build();

    }
}