package com.example.demo.jwt_security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

/**
 * Created on May, 2022
 *
 * @author destan
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	protected JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(new BearerTokenRequestMatcher(), authenticationManager);
		this.setAuthenticationSuccessHandler((request, response, authentication) -> { /* noop success handler*/ });
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		final String token = tokenFromRequest(request);
		final Authentication authRequest = JwtAuthentication.unauthenticated(token);
		// this.getAuthenticationManager() returns ProviderManager
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// As this authentication is in HTTP header, after success we need to continue the request normally
		// Failure to do so will cause an infinite redirect loop.
		chain.doFilter(request, response);
	}

	private String tokenFromRequest(HttpServletRequest request) {
		final String value = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (value == null || !value.toLowerCase().startsWith("bearer")) {
			return null;
		}

		String[] parts = value.split(" ");

		if (parts.length < 2) {
			return null;
		}

		return parts[1].trim();
	}

	private static final class BearerTokenRequestMatcher implements RequestMatcher {

		@Override
		public boolean matches(HttpServletRequest request) {
			return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
		}

	}
}
