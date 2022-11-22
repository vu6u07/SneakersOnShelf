package com.sos.controller;

import javax.security.sasl.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.dto.LoginRequest;
import com.sos.service.AuthenticationService;

@RestController
@RequestMapping(value = "/api/v1/tokens")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authenticationService.signin(loginRequest));
	}

	@PostMapping(value = "/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) throws AuthenticationException {
		return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
	}

}
