package com.sos.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.security.core.AuthenticationException;

import com.sos.dto.JwtResponse;
import com.sos.dto.LoginRequest;
import com.sos.dto.RegisterRequest;

public interface AuthenticationService {

	public JwtResponse signin(LoginRequest loginRequest);

	public JwtResponse signup(RegisterRequest register);

	public JwtResponse refreshToken(String refreshToken) throws AuthenticationException;

	void updateAccountPassword(int id, String password, String newPassword);
	
	void resetAccountPassword(String username, String email) throws UnsupportedEncodingException, MessagingException;

}
