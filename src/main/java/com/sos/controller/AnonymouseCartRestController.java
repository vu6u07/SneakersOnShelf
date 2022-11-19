package com.sos.controller;

import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

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
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;
import com.sos.entity.Voucher;
import com.sos.service.CartService;
import com.sos.service.util.ValidationUtil;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class AnonymouseCartRestController {

	@Autowired
	private CartService<String> anonymouseCartService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Validator validator;

	@GetMapping(value = "/anonymous")
	public ResponseEntity<?> getCart() {
		return ResponseEntity.ok(anonymouseCartService.createCart(null));
	}

	@GetMapping(value = "/{id}", headers = { "token" })
	public ResponseEntity<?> getCartById(@PathVariable(name = "id") int id,
			@RequestHeader(name = "token") String token) {
		return ResponseEntity.ok(anonymouseCartService.getCartDTOById(id, token));
	}

	@PostMapping(value = "/{id}/items", headers = { "token" })
	public ResponseEntity<?> addToCart(@PathVariable(name = "id") int id,
			@RequestParam(name = "product_detail_id") int productDetailId,
			@RequestParam(name = "quantity") int quantity, @RequestHeader(name = "token") String token) {
		System.out.println("add to cart");
		anonymouseCartService.addToCart(id, productDetailId, quantity, token);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/items/{id}", headers = { "token" })
	public ResponseEntity<?> setQuantityCartItem(@PathVariable(name = "id") int id,
			@RequestParam(name = "quantity") int quantity, @RequestHeader(name = "token") String token) {
		anonymouseCartService.changeCartItemQuantity(id, quantity, token);
		return ResponseEntity.noContent().build();
	}

	// @formatter:off
	@DeleteMapping(value = "/items/{id}", headers = { "token" })
	public ResponseEntity<?> deleteCartItem(
			@PathVariable(name = "id") int id, 
			@RequestHeader(name = "token") String token) {
		anonymouseCartService.deleteCartItem(id, token);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

	// @formatter:off
	@DeleteMapping(value = "/{id}/items", headers = { "token" })
	public ResponseEntity<?> deleteAllCartItem(
			@PathVariable(name = "id") int id, 
			@RequestHeader(name = "token") String token) {
		anonymouseCartService.deleteAllCartItem(id, token);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

	@PostMapping(value = "/{id}/submit", headers = { "token" })
	public ResponseEntity<?> submitCart(@PathVariable(name = "id") int id, @RequestHeader(name = "token") String token,
			@RequestBody JsonNode data, HttpServletRequest request)
			throws JsonProcessingException, IllegalArgumentException, URISyntaxException {
		CustomerInfo customerInfo = objectMapper.treeToValue(data.get("customerInfo"), CustomerInfo.class);

		Set<ConstraintViolation<CustomerInfo>> violations = validator.validate(customerInfo);
		if (!violations.isEmpty()) {
			String errorsMsg = violations.stream().map(ConstraintViolation<CustomerInfo>::getMessage)
					.collect(Collectors.joining(", "));
			throw new ValidationException(errorsMsg);
		}
		ValidationUtil.validatePhone(customerInfo.getPhone());
		
		String email = data.get("email").asText();
		ValidationUtil.validateEmail(email);

		Voucher voucher = objectMapper.treeToValue(data.get("voucher"), Voucher.class);

		Order order = anonymouseCartService.submitCart(id, customerInfo, email, SaleMethod.DELIVERY, voucher, token);
		return ResponseEntity.ok(order.getId());
	}

}
