package com.sos.controller.admin;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
import com.sos.common.ApplicationConstant.CartStatus;
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;
import com.sos.entity.Voucher;
import com.sos.security.AccountAuthentication;
import com.sos.service.AdminCartService;
import com.sos.service.CustomerInfoService;
import com.sos.service.util.ValidationUtil;

@RestController
@RequestMapping(value = "/admin/v1")
public class CartAdminController {

	@Autowired
	@Qualifier("AdminCartServiceImpl")
	private AdminCartService cartService;

	@Autowired
	private CustomerInfoService customerInfoService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Validator validator;

	// @formatter:off
	@GetMapping(value = "/carts")
	public ResponseEntity<?> get(
			@RequestParam(name = "status", defaultValue = "PENDING") CartStatus cartStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.findCartReportDTO(authentication, cartStatus, PageRequest.of(page - 1, size, Sort.by("updateDate").descending())));
	}

	@GetMapping(value = "/carts", params = {"query"})
	public ResponseEntity<?> get(
			@RequestParam(name = "query") int id,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.findCartReportDTO(authentication, id, PageRequest.of(page - 1, size, Sort.by("updateDate").descending())));
	}
	
	@PostMapping(value = "/carts")
	public ResponseEntity<?> getCart(AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.createCart(authentication));
	}
	
	@GetMapping(value = "/carts/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		return ResponseEntity.ok(cartService.getCartDTOById(id, authentication));
	}
	
	@PostMapping(value = "/carts/{id}/items")
	public ResponseEntity<?> addToCart(
			@PathVariable(name = "id") int id,
			@RequestParam(name = "product_detail_id") int productDetailId,
			@RequestParam(name = "quantity") int quantity,
			AccountAuthentication authentication) {
		cartService.addToCart(id, productDetailId, quantity, authentication);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/carts/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		cartService.deleteCart(id, authentication);
		return ResponseEntity.noContent().build();
	}
	
	
	@PutMapping(value = "/carts/items/{id}")
	public ResponseEntity<?> setQuantityCartItem(
			@PathVariable(name = "id") int id,
			@RequestParam(name = "quantity") int quantity, 
			AccountAuthentication authentication) {
		cartService.changeCartItemQuantity(id, quantity, authentication);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/carts/items/{id}")
	public ResponseEntity<?> deleteCartItem(@PathVariable(name = "id") int id, AccountAuthentication authentication) {
		cartService.deleteCartItem(id, authentication);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/carts/{id}/items")
	public ResponseEntity<?> deleteAllCartItem(
			@PathVariable(name = "id") int id, 
			AccountAuthentication authentication) {
		cartService.deleteAllCartItem(id, authentication);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/carts/{id}/submit")
	public ResponseEntity<?> submitCartShipping(
			@PathVariable(name = "id") int id, 
			@RequestBody JsonNode data,
			AccountAuthentication authentication) 
			throws JsonProcessingException, IllegalArgumentException {
		
		SaleMethod saleMethod = SaleMethod.valueOf(data.get("saleMethod").asText());
		CustomerInfo customerInfo = mapper.treeToValue(data.get("customerInfo"), CustomerInfo.class);
		if(saleMethod == SaleMethod.DELIVERY) {
			Set<ConstraintViolation<CustomerInfo>> violations = validator.validate(customerInfo);
			if (!violations.isEmpty()) {
				String errorsMsg = violations.stream().map(ConstraintViolation<CustomerInfo>::getMessage)
						.collect(Collectors.joining(", "));
				throw new ValidationException(errorsMsg);
			}
			ValidationUtil.validatePhone(customerInfo.getPhone());
			
			if(customerInfo.getId() == 0 && customerInfo.getAccount() != null) {
				CompletableFuture.runAsync(() -> {
					customerInfoService.save(customerInfo);
				});
			}
		}
		
		String email = null;
		if(!data.get("email").isNull()) {
			email = data.get("email").asText();
			ValidationUtil.validateEmail(email);
		}
		
		Voucher voucher = mapper.treeToValue(data.get("voucher"), Voucher.class);
		
		Order order = cartService.submitCart(id, customerInfo, email, saleMethod, voucher, authentication);
		return ResponseEntity.ok(order);
	}
	
	// @formatter:on

}
