package com.example.demo.jwt_security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.RegisteredClaims;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final JWTVerifier jwtVerifier;

	private final InvalidJwtStore invalidJwtStore;

	private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	public JwtAuthenticationProvider(InvalidJwtStore invalidJwtStore, byte[] secret) {
		this.invalidJwtStore = invalidJwtStore;

		//@formatter:off
		this.jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
							  .withClaimPresence(RegisteredClaims.JWT_ID)
							  .withClaimPresence(RegisteredClaims.AUDIENCE)
							  .withClaimPresence(RegisteredClaims.SUBJECT)
							  .withClaimPresence(RegisteredClaims.ISSUER)
							  .build();
		//@formatter:on
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(JwtAuthentication.class, authentication,
				() -> this.messages.getMessage("JwtAuthenticationProvider.onlySupports",
						"Only JwtAuthentication is supported"));

		final JwtAuthentication jwt = (JwtAuthentication) authentication;
		try {
			invalidJwtStore.verify(jwt.getToken());
			jwt.verify(jwtVerifier);
			if (log.isDebugEnabled()) {
				log.debug("Authenticated with jwt with scopes {}", jwt.getAuthorities());
			}
			return jwt;
		}
		catch (JWTVerificationException e) {
			log.error(e.getMessage());
			throw new BadCredentialsException("Not a valid token", e);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthentication.class.isAssignableFrom(authentication);
	}

}
