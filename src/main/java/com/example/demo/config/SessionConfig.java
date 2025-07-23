//package com.example.demo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.example.demo.interceptor.AuthenticationInterceptor;
//
//@Configuration
//public class SessionConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthenticationInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                    "/users/login",
//                    "/users/register",
//                    "/assets/css/**",
//                    "/assets/js/**",
//                    "/assets/images/**",
//                    "/favicon.ico",
//                    "/error"
//                );
//    }
//}
