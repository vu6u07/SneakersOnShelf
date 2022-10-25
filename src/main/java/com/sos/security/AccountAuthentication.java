package com.sos.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AccountAuthentication extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private int id;

	public AccountAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.id = Integer.parseInt(principal.toString());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
