package com.sos.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.security.core.AuthenticationException;

import com.sos.dto.CreateAccountRequestDTO;
import com.sos.dto.JwtResponse;
import com.sos.dto.LoginRequest;
import com.sos.dto.RegisterRequest;
import com.sos.entity.Account;

public interface AuthenticationService {

	JwtResponse signin(LoginRequest loginRequest);

	JwtResponse signup(RegisterRequest register);

	JwtResponse refreshToken(String refreshToken) throws AuthenticationException;

	void updateAccountPassword(int id, String password, String newPassword);

	void resetAccountPassword(String username, String email) throws UnsupportedEncodingException, MessagingException;
	
	String resetAccountPassword(int id);

	Account signup(CreateAccountRequestDTO account) throws UnsupportedEncodingException, MessagingException;
	
	void signup(List<CreateAccountRequestDTO> accounts) throws UnsupportedEncodingException, MessagingException;
}
