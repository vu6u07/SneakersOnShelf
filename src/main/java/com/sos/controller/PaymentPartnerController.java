package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.security.AccountAuthentication;
import com.sos.service.PaymentPartnerService;
import com.sos.service.PurchaseService;

@RestController
@RequestMapping(value = "/api/v1")
public class PaymentPartnerController {

	@Autowired
	private PaymentPartnerService<PurchaseInfoDTO> paymentPartnerService;

	@Autowired
	private PurchaseService purchaseService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/purchases/{id}/pay-url")
	public ResponseEntity<?> getPayUrl(@PathVariable(name = "id") String id, AccountAuthentication authentication)
			throws Exception {
		return ResponseEntity.ok(paymentPartnerService.getPayUrl(purchaseService.findPurchaseDTO(id, authentication)));
	}
	
	@GetMapping(value = "/purchases/{id}/pay-url", headers = { "token" })
	public ResponseEntity<?> getPayUrl(@PathVariable(name = "id") String id, @RequestHeader(name = "token") String userTokenQuery) throws Exception {
		return ResponseEntity.ok(paymentPartnerService.getPayUrl(purchaseService.findPurchaseDTO(id, userTokenQuery)));
	}

	@PostMapping(value = "/partner/payment/transactions/{id}")
	public ResponseEntity<?> confirmTransactions(@PathVariable(name = "id") int id, @RequestBody JsonNode data) throws Exception {
		paymentPartnerService.confirmTransaction(id, data);
		return ResponseEntity.noContent().build();
	}
}
