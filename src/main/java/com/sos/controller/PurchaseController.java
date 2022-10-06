package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.PurchaseService;

@RestController
@RequestMapping(value = "/content/v1/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getPurchase(@PathVariable(name = "id") int id,
			@RequestHeader(name = "user_token_query") String userTokenQuery) {
		return ResponseEntity.ok(purchaseService.findPurchaseDTO(id, userTokenQuery));
	}

}
