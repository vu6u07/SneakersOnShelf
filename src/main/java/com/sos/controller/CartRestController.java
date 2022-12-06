package com.sos.controller;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;
import com.sos.entity.Voucher;
import com.sos.security.AccountAuthentication;
import com.sos.service.CartService;
import com.sos.service.CustomerInfoService;
import com.sos.service.impl.GoogleFCMNotificationService;
import com.sos.service.util.ValidationUtil;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartRestController {

	@Autowired
	@Qualifier("CartServiceImpl")
	private CartService<AccountAuthentication> cartService;
	
	@Autowired
	private GoogleFCMNotificationService notificationService;
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Validator validator;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<?> getCart(AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.createCart(authentication));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCartById(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.getCartDTOById(id, authentication));
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
		CustomerInfo customerInfo = mapper.treeToValue(data.get("customerInfo"), CustomerInfo.class);
		Set<ConstraintViolation<CustomerInfo>> violations = validator.validate(customerInfo);
		if (!violations.isEmpty()) {
			String errorsMsg = violations.stream().map(ConstraintViolation<CustomerInfo>::getMessage)
					.collect(Collectors.joining(", "));
			throw new ValidationException(errorsMsg);
		}
		ValidationUtil.validatePhone(customerInfo.getPhone());
		
		if(customerInfo.getId() == 0) {
			CompletableFuture.runAsync(() -> {
				customerInfoService.save(customerInfo);
			});
		}
		
		Voucher voucher = mapper.treeToValue(data.get("voucher"), Voucher.class);
		
		Order order = cartService.submitCart(id, customerInfo, null, SaleMethod.DELIVERY, voucher, authentication);
		notificationService.sendNotificationOnNewOrder(order.getId());
		return ResponseEntity.ok(order);
	}
	// @formatter:on

}
