package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(mvc.pattern("/hello")).authenticated()
                .requestMatchers(mvc.pattern("/demo/**")).authenticated()
                .requestMatchers(mvc.pattern(HttpMethod.POST, "/posts")).hasAuthority("ADMIN")
                .requestMatchers(mvc.pattern("/h2-console"), mvc.pattern("/h2-console/**")).permitAll()
                .anyRequest().permitAll()
            )
            .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
            .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout((logout) -> logout.permitAll())
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user =
    //             User.withDefaultPasswordEncoder()
    //                     .username("joe")
    //                     .password("123qwe123")
    //                     // .roles("USER")
    //                     .authorities("USER")
    //                     .build();
    //
    //     UserDetails admin =
    //             User.withDefaultPasswordEncoder()
    //                     .username("jane")
    //                     .password("123qwe123")
    //                     .roles("ADMIN")
    //                     .build();
    //
    //     return new InMemoryUserDetailsManager(user, admin);
    // }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
