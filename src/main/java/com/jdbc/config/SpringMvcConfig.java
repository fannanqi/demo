//package com.jdbc.config;
//
//import com.jdbc.handler.UserLoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.*;
//
//@Configuration
//@EnableWebMvc
//public class SpringMvcConfig implements WebMvcConfigurer {
//    @Autowired
//    private UserLoginInterceptor userLoginInterceptor;
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("/static/");
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration interceptorRegistration = registry.addInterceptor(userLoginInterceptor);
//        interceptorRegistration.addPathPatterns("/**");
//        interceptorRegistration.excludePathPatterns(
//                "/jdbc/login",
//                "/jdbc/regist",
//                "/*.html",
//                "/js/**",
//                "/element-ui/**"
//        );
//    }
//}
