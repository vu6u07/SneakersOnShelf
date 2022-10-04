package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.CartService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/content/v1/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping
	public ResponseEntity<?> getCart() {
		return ResponseEntity.ok(cartService.getCartDTO());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCartById(@PathVariable(name = "id") int id,
			@RequestParam(name = "user_token_query") String userTokenQuery) {
		return ResponseEntity.ok(cartService.getCartDTO(id, userTokenQuery));
	}

	@PostMapping(value = "/{id}/items")
	public ResponseEntity<?> addToCart(@PathVariable(name = "id") int id,
			@RequestParam(name = "product_id") int productId,
			@RequestParam(name = "user_token_query") String userTokenQuery) {
		cartService.addToCart(id, productId, userTokenQuery);
		return ResponseEntity.noContent().build();
	}

}
