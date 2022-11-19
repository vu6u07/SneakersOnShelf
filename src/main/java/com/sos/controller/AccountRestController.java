package com.sos.controller;

import com.sos.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.AccountService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class AccountRestController {

	@Autowired
	private AccountService accountService;

	@PreAuthorize(value = "hasRole('ROLE_USER') and #id == authentication.id")
	@GetMapping(value = "/accounts/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(accountService.findAccountDTOById(id));
	}


}
