package com.example.demo.jwt_security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	// Thanks: https://stackoverflow.com/a/71449312/878361
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	JwtConfigurer jwtConfigurer() {
		return new JwtConfigurer();
	}

	@Scope("prototype")
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http, MvcRequestMatcher.Builder mvc, InvalidJwtStore invalidJwtStore, @Value("${sample-app.jwt.secret}") final byte[] secret) throws Exception {

		http.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(mvc.pattern("/h2-console"), mvc.pattern("/h2-console/**")).permitAll()
				.requestMatchers(mvc.pattern("/demo"), mvc.pattern("/login")).permitAll()
				.anyRequest().authenticated());

		http.authenticationProvider(new JwtAuthenticationProvider(invalidJwtStore, secret))
			.exceptionHandling(exceptionHandling -> exceptionHandling
									.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
									.accessDeniedHandler(new JwtAccessDeniedHandler())
					)
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.apply(jwtConfigurer());
		//@formatter:on

		http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.disable());

		return http.build();
	}

}

