package com.sos.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.sos.entity.CustomerInfo;
import com.sos.security.AccountAuthentication;
import com.sos.service.CustomerInfoService;
import com.sos.service.util.ValidationUtil;

@RestController
@RequestMapping(value = "/api/v1")
public class CustomerInfoRestController {

	@Autowired
	private CustomerInfoService customerInfoService;

	@PreAuthorize(value = "hasRole('ROLE_USER') and #id == authentication.id")
	@GetMapping(value = "/accounts/{id}/customer-infos")
	public ResponseEntity<?> getAllCustomerInfoByAccount(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(customerInfoService.findByAccountId(id));
	}
	
	@GetMapping(value = "/customers")
	public ResponseEntity<?> getAllCustomerInfo() {
		return ResponseEntity.ok(customerInfoService.findAll());
	}

	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PostMapping(value = "/customer-infos")
	public ResponseEntity<?> post(@RequestBody @Valid CustomerInfo customerInfo, AccountAuthentication authentication,
			HttpServletRequest request) throws URISyntaxException {
		ValidationUtil.validatePhone(customerInfo.getPhone());
		CustomerInfo created = customerInfoService.save(customerInfo, authentication);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.body(created);
	}

	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PutMapping(value = "/customer-infos/{id}/set-default")
	public ResponseEntity<?> setDefault(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		customerInfoService.setDefaultCustomerInfo(id, authentication);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PutMapping(value = "/customer-infos/{id}/deactive")
	public ResponseEntity<?> deactive(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		customerInfoService.deactivateCustomerInfo(id, authentication);
		return ResponseEntity.noContent().build();
	}

}
