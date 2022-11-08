package com.sos.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.AccountService;

@RestController
@RequestMapping(value = "/admin/v1/")
public class AccountAdminController {
	
	@Autowired
	private AccountService accountService;
	
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/accounts")
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(accountService.findAll());
	}
	
}
