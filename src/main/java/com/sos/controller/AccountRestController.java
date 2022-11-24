package com.sos.controller;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.sos.dto.ChangePasswordRequest;
import com.sos.dto.RegisterRequest;
import com.sos.service.AccountService;
import com.sos.service.AuthenticationService;
import com.sos.service.util.ValidationUtil;

@RestController
@RequestMapping(value = "/api/v1")
public class AccountRestController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private Validator validator;

	@PreAuthorize(value = "hasRole('ROLE_USER') and #id == authentication.id")
	@GetMapping(value = "/accounts/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(accountService.findAccountDTOById(id));
	}

	@PostMapping(value = "/accounts")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authenticationService.signup(request));
	}

	@PreAuthorize(value = "hasRole('ROLE_USER') and #id == authentication.id")
	@PutMapping(value = "/accounts/{id}/info")
	public ResponseEntity<?> updateAccountInfo(@PathVariable(name = "id") int id,
			@RequestBody RegisterRequest request) {
		Set<ConstraintViolation<RegisterRequest>> violations = validator.validateProperty(request, "fullname");
		violations.addAll(validator.validateProperty(request, "email"));
		ValidationUtil.validateEmail(request.getEmail());
		if (!violations.isEmpty()) {
			throw new ValidationException(violations.stream().map(ConstraintViolation<RegisterRequest>::getMessage)
					.collect(Collectors.joining(", ")));
		}
		accountService.updateAccountInfo(id, request.getFullname(), request.getEmail());
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize(value = "hasRole('ROLE_USER') and #id == authentication.id")
	@PutMapping(value = "/accounts/{id}/password")
	public ResponseEntity<?> updateAccountPassword(@PathVariable(name = "id") int id,
			@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
		authenticationService.updateAccountPassword(id, changePasswordRequest.getPassword(),
				changePasswordRequest.getNewPassword());
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/accounts/reset-password")
	public ResponseEntity<?> resetAccountPassword(@RequestBody JsonNode data) throws UnsupportedEncodingException, MessagingException {
		authenticationService.resetAccountPassword(data.get("username").asText(), data.get("email").asText());
		return ResponseEntity.noContent().build();
	}

}
