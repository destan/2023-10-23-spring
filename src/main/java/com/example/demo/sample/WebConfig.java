package com.example.demo.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
class WebConfig {

    @Bean // <bean class="com.example.demo.sample.Translator"></bean>
    Translator translator() {
        return new Translator();
    }

    // @Bean
    // public MethodValidationPostProcessor methodValidationPostProcessor() {
    //     return new MethodValidationPostProcessor();
    // }

}

