package com.example.demo.jwt_security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

/**
 * Created on May, 2022
 *
 * @author destan
 */
class JwtConfigurer extends AbstractHttpConfigurer<JwtConfigurer, HttpSecurity> {

	@Override
	public void configure(HttpSecurity builder) {
		final AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
		builder.addFilterBefore(new JwtAuthenticationFilter(authenticationManager), AuthorizationFilter.class);
	}
}
