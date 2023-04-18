package com.project.eventlog.config;


import com.project.eventlog.config.interceptors.AdminInterceptor;
import com.project.eventlog.config.interceptors.UserProfileInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private UserProfileInterceptor userProfileInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userProfileInterceptor)
                .addPathPatterns("/users/{userId}/edit", "/users/{userId}/change-username");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/users/management")
                .addPathPatterns("/users/{userId}/change-role");
    }
}