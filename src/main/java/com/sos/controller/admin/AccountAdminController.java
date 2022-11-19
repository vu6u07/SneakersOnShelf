package com.sos.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.AccountService;

@RestController
@RequestMapping(value = "/admin/v1")
public class AccountAdminController {

	@Autowired
	private AccountService accountService;

	// @formatter:off
	@GetMapping(value = "/accounts")
	public ResponseEntity<?> get(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(accountService.findAccoutDTOs(PageRequest.of(page - 1, size)));
	}
	
	@GetMapping(value = "/accounts", params = {"query"})
	public ResponseEntity<?> get(
			@RequestParam(name = "query") String query,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(accountService.findAccoutDTOs(query, PageRequest.of(page - 1, size)));
	}
	// @formatter:on

	@GetMapping(value = "/accounts/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(accountService.findAccountReportDTOById(id));
	}
}
