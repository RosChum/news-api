package com.example.newsapi.configuration;

import com.example.newsapi.interceptor.HandlerInterceptorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(getInterceptor());
    }

    @Bean
    public HandlerInterceptorImpl getInterceptor(){
        return new HandlerInterceptorImpl();
    }
}
