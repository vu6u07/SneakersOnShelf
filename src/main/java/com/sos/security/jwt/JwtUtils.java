package com.sos.security.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.sos.security.AccountAuthentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	private final String AUTHORITIES_KEY = "auth";

	@Value("${security.jwt.secret}")
	private String jwtSecret;

	@Value("${security.jwt.refresh-secret}")
	private String jwtRefreshSecret;

	@Value("${security.jwt.expiration}")
	private long jwtExpirationMs;

	public String generateToken(String subject, Collection<? extends GrantedAuthority> collection) {
		Date empiredDate = new Date(new Date().getTime() + jwtExpirationMs);
		String authorities = collection.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		return Jwts.builder().setSubject(subject).claim(AUTHORITIES_KEY, authorities).setExpiration(empiredDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String generateRefreshToken(String subject, Date empiredDate) {
		return Jwts.builder().setSubject(subject).setExpiration(empiredDate)
				.signWith(SignatureAlgorithm.HS512, jwtRefreshSecret).compact();
	}

	public Authentication getAuthenticationByToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		Collection<GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		return new AccountAuthentication(claims.getSubject(), null, authorities);
	}

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public boolean validateJwtRefreshToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

}
