package com.example.demo.jwt_security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import java.io.IOException;

/**
 * Custom handler for access denied exceptions.
 */
class JwtAccessDeniedHandler extends AccessDeniedHandlerImpl {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"Insufficient scope\"");
		super.handle(request, response, accessDeniedException);
	}
}
