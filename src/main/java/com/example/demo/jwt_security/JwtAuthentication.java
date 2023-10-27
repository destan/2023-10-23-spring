package com.example.demo.jwt_security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This is the {@code Principle} object that is injected to controller methods
 */
public class JwtAuthentication implements Authentication {

	private final static String SCOPE_AUTHORITY_PREFIX = "SCOPE_";

	private final static String PERMISSION_AUTHORITY_PREFIX = "PERMISSION_";

	private final DecodedJWT decoded;

	private boolean authenticated;

	private final User user;

	private JwtAuthentication(String token) throws JWTVerificationException {
		this.decoded = JWT.decode(token);
		this.authenticated = false;
		this.user = new User(decoded);
	}

	/**
	 * @see UsernamePasswordAuthenticationFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)
	 */
	public static Authentication unauthenticated(String token) {
		return new JwtAuthentication(token);
	}

	public String getToken() {
		return decoded.getToken();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> scopes = getScopeAuthorities();
		List<SimpleGrantedAuthority> permissions = getPermissionAuthorities();

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.addAll(scopes);
		authorities.addAll(permissions);

		return authorities;
	}

	@Override
	public Object getCredentials() {
		return decoded.getToken();
	}

	@Override
	public Object getDetails() {
		return decoded;
	}

	/**
	 * This is the object that @AuthenticationPrincipal injects into controller methods
	 * @return The user
	 */
	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		throw new UnsupportedOperationException("Must create a new instance to specify that the authentication is valid");
	}

	@Override
	public String getName() {
		return decoded.getSubject();
	}

	void verify(@NonNull JWTVerifier jwtVerifier) throws JWTVerificationException {
		jwtVerifier.verify(getToken());
		this.authenticated = true;
	}

	private List<SimpleGrantedAuthority> getScopeAuthorities() {
		String scope = decoded.getClaim("scope").asString();
		if (scope == null || scope.trim().isEmpty()) {
			return Collections.emptyList();
		}
		final String[] scopes = scope.split(" ");
		List<SimpleGrantedAuthority> authorities = new ArrayList<>(scopes.length * 2);
		for (String value : scopes) {
			// For backwards-compatibility, create authority without scope prefix
			authorities.add(new SimpleGrantedAuthority(value));
			authorities.add(new SimpleGrantedAuthority(SCOPE_AUTHORITY_PREFIX + value));
		}
		return authorities;
	}

	private List<SimpleGrantedAuthority> getPermissionAuthorities() {
		String[] permissions = decoded.getClaim("permissions").asArray(String.class);

		if (permissions == null || permissions.length == 0) {
			return Collections.emptyList();
		}

		List<SimpleGrantedAuthority> authorities = new ArrayList<>(permissions.length);
		for (String value : permissions) {
			authorities.add(new SimpleGrantedAuthority(PERMISSION_AUTHORITY_PREFIX + value));
		}

		return authorities;
	}
}
