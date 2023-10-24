package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WebConfig {

    @Bean // <bean class="com.example.demo.Translator"></bean>
    Translator translator() {
        return new Translator();
    }

}

