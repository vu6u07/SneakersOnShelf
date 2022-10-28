package com.sos.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.sos.dto.JwtResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {

	private JwtResponse jwtResponse;
	private String name;
	private Map<String, Object> attributes;
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return name;
	}

}
