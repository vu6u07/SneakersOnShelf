package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.AccountService;

@RestController
@RequestMapping(value = "/api/v1")
public class AccountRestController {

	@Autowired
	private AccountService accountService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/accounts")
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(accountService.findAll());
	}

	@PreAuthorize(value = "hasRole('ROLE_USER') and #id == authentication.id")
	@GetMapping(value = "/accounts/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.of(accountService.findAccountDTOById(id));
	}

}