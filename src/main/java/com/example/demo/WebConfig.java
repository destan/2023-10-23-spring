package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
    }

    // https://github.com/spring-projects/spring-framework/issues/28552/
    // @Override
    // public void configurePathMatch(PathMatchConfigurer configurer) {
    //     configurer.setUseTrailingSlashMatch(true);
    // }
}