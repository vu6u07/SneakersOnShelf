package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.entity.CustomerInfo;
import com.sos.service.CartService;

@RestController
@RequestMapping(value = "/content/v1/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public ResponseEntity<?> getCart() {
		return ResponseEntity.ok(cartService.getCartDTO());
	}

	@GetMapping(value = "/{id}", headers = { "user_token_query" })
	public ResponseEntity<?> getCartById(@PathVariable(name = "id") int id,
			@RequestHeader(name = "user_token_query") String userTokenQuery) {
		return ResponseEntity.ok(cartService.getCartDTO(id, userTokenQuery));
	}

	@PostMapping(value = "/{id}/items", headers = { "user_token_query" })
	public ResponseEntity<?> addToCart(@PathVariable(name = "id") int id,
			@RequestParam(name = "product_id") int productId, @RequestParam(name = "quantity") int quantity,
			@RequestHeader(name = "user_token_query") String userTokenQuery) {
		cartService.addToCart(id, productId, quantity, userTokenQuery);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/items/{id}", headers = { "user_token_query" })
	public ResponseEntity<?> setQuantityCartItem(@PathVariable(name = "id") int id,
			@RequestParam(name = "quantity") int quantity,
			@RequestHeader(name = "user_token_query") String userTokenQuery) {
		cartService.changeCartItemQuantity(id, quantity, userTokenQuery);
		return ResponseEntity.noContent().build();
	}

	// @formatter:off
	@DeleteMapping(value = "/items/{id}", headers = { "user_token_query" })
	public ResponseEntity<?> deleteCartItem(
			@PathVariable(name = "id") int id, 
			@RequestHeader(name = "user_token_query") String userTokenQuery) {
		cartService.deleteCartItem(id, userTokenQuery);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

	// @formatter:off
	@PostMapping(value = "/{id}/submit", headers = { "user_token_query" })
	public ResponseEntity<?> submitCart(
			@PathVariable(name = "id") int id, 
			@RequestHeader(name = "user_token_query") String userTokenQuery,
			@RequestBody JsonNode data) 
			throws JsonProcessingException, IllegalArgumentException {
		cartService.submitCart(id, userTokenQuery, objectMapper.treeToValue(data.get("customer_info"), CustomerInfo.class), PaymentMethod.valueOf(data.get("payment_method").asText()));
		return ResponseEntity.ok(userTokenQuery);
	}
	// @formatter:on

}
