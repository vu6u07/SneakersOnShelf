package com.sos.service;

import javax.validation.Valid;

import org.springframework.security.core.AuthenticationException;

import com.sos.dto.JwtResponse;
import com.sos.dto.LoginRequest;

public interface AuthenticationService {

	public JwtResponse signin(LoginRequest loginRequest);

	public JwtResponse signup(@Valid LoginRequest loginRequest);

	public JwtResponse refreshToken(String refreshToken) throws AuthenticationException;

}
