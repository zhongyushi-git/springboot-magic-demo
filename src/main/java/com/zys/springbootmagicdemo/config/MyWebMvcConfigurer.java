package com.zys.springbootmagicdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置,注入拦截器
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns表示拦截所有请求，excludePathPatterns表示不拦截的请求
        registry.addInterceptor(myInterceptor).addPathPatterns("/swagger-ui.html").addPathPatterns("/magic/**");
    }
}
