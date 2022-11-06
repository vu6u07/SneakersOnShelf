package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.entity.Order;
import com.sos.security.AccountAuthentication;
import com.sos.service.CartService;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartRestController {

	@Autowired
	private CartService cartService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<?> getCart(AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.getOrCreateCart(authentication));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCartById(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.getCartDTO(id, authentication));
	}

	// @formatter:off
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/{id}/items")
	public ResponseEntity<?> addToCart(
			@PathVariable(name = "id") int id,
			@RequestParam(name = "product_detail_id") int productDetailId,
			@RequestParam(name = "quantity") int quantity,
			AccountAuthentication authentication) {
		cartService.addToCart(id, productDetailId, quantity, authentication);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

	// @formatter:off
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping(value = "/items/{id}")
	public ResponseEntity<?> setQuantityCartItem(
			@PathVariable(name = "id") int id,
			@RequestParam(name = "quantity") int quantity, 
			AccountAuthentication authentication) {
		cartService.changeCartItemQuantity(id, quantity, authentication);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

	// @formatter:off
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping(value = "/items/{id}")
	public ResponseEntity<?> deleteCartItem(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		cartService.deleteCartItem(id, authentication);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

	// @formatter:off
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping(value = "/{id}/items")
	public ResponseEntity<?> deleteAllCartItem(
			@PathVariable(name = "id") int id, 
			AccountAuthentication authentication) {
		cartService.deleteAllCartItem(id, authentication);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

	// @formatter:off
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/{id}/submit")
	public ResponseEntity<?> submitCart(
			@PathVariable(name = "id") int id, 
			@RequestBody JsonNode data,
			AccountAuthentication authentication) 
			throws JsonProcessingException, IllegalArgumentException {
		Order order = cartService.submitCart(id, PaymentMethod.valueOf(data.get("payment_method").asText()), data.get("customer_info").get("id").asInt(), authentication);
		return ResponseEntity.ok(order.getId());
	}
	// @formatter:on

}
