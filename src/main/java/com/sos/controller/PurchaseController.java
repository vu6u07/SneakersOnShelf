package com.sos.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.security.AccountAuthentication;
import com.sos.service.PurchaseService;

@RestController
@RequestMapping(value = "/api/v1")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@GetMapping(value = "/purchases/{id}", headers = { "token" })
	public ResponseEntity<?> getAnonymousPurchase(@PathVariable(name = "id") String id,
			@RequestHeader(name = "token") String userTokenQuery) {
		return ResponseEntity.ok(purchaseService.findPurchaseDTO(UUID.fromString(id), userTokenQuery));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/purchases/{id}")
	public ResponseEntity<?> getPurchase(@PathVariable(name = "id") String id, AccountAuthentication authentication) {
		return ResponseEntity.ok(purchaseService.findPurchaseDTO(UUID.fromString(id), authentication));
	}

	@PreAuthorize("hasRole('ROLE_USER') and #id == authentication.id")
	@GetMapping(value = "/accounts/{id}/purchases")
	public ResponseEntity<?> getPurchaseByUserId(@PathVariable(name = "id") int id,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size, AccountAuthentication authentication) {
		return ResponseEntity
				.ok(purchaseService.findAllPurchaseDTOByAccountId(authentication, PageRequest.of(page - 1, size)));
	}

}
