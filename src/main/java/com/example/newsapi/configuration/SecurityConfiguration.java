package com.example.newsapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/api/register").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/news").permitAll()
                .requestMatchers("/api/signin").hasAnyRole("ADMIN", "USER", "MODERATOR")
                .anyRequest().authenticated()).csrf(AbstractHttpConfigurer::disable);


        return httpSecurity.build();

    }
}
