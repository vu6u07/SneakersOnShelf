package com.sos.controller.admin;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.dto.CreateAccountRequestDTO;
import com.sos.entity.Account;
import com.sos.service.AccountService;
import com.sos.service.AuthenticationService;
import com.sos.service.util.ValidationUtil;

@RestController
@RequestMapping(value = "/admin/v1")
public class StaffAdminController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private Validator validator;

	// @formatter:off
	@GetMapping(value = "/staff")
	public ResponseEntity<?> get(@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "status", required = false) AccountStatus accountStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(accountService.findStaffAccoutDTOs(query, accountStatus, PageRequest.of(page - 1, size)));
	}
	// @formatter:on

	@PostMapping(value = "/staff")
	public ResponseEntity<?> post(@RequestBody CreateAccountRequestDTO account, HttpServletRequest request)
			throws URISyntaxException, UnsupportedEncodingException, MessagingException {
		ValidationUtil.validateEmail(account.getEmail());
		ValidationUtil.validateUsername(account.getUsername());
		account.setAdmin(true);
		Account created = authenticationService.signup(account);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.body(created.getId());

	}

	@GetMapping(value = "/staff/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(accountService.findStaffAccountReportDTOById(id));
	}

	@PutMapping(value = "/staff/{id}/info")
	public ResponseEntity<?> updateAccountInfo(@PathVariable(name = "id") int id,
			@RequestBody CreateAccountRequestDTO request) {
		Set<ConstraintViolation<CreateAccountRequestDTO>> violations = validator.validateProperty(request, "fullname");
		violations.addAll(validator.validateProperty(request, "email"));
		ValidationUtil.validateEmail(request.getEmail());
		if (!violations.isEmpty()) {
			throw new ValidationException(violations.stream()
					.map(ConstraintViolation<CreateAccountRequestDTO>::getMessage).collect(Collectors.joining(", ")));
		}
		accountService.updateAccountInfo(id, request.getFullname(), request.getEmail());
		return ResponseEntity.noContent().build();
	}

}
