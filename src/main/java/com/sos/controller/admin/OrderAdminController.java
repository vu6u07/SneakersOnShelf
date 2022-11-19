package com.sos.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.security.AccountAuthentication;
import com.sos.service.OrderService;

@RestController
@RequestMapping(value = "/admin/v1")
public class OrderAdminController {

	@Autowired
	private OrderService orderService;

	// @formatter:off
	@GetMapping(value = "/orders")
	public ResponseEntity<?> get(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(orderService.findPurchaseDTOs(PageRequest.of(page - 1, size, Sort.by("createDate").descending())));
	}
	
	@GetMapping(value = "/orders", params = "status")
	public ResponseEntity<?> get(
			@RequestParam(name = "status") OrderStatus orderStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(orderService.findPurchaseDTOs(orderStatus, PageRequest.of(page - 1, size, Sort.by("createDate").descending())));
	}

	@GetMapping(value = "/orders", params = {"query"})
	public ResponseEntity<?> get(
			@RequestParam(name = "query") String query,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(orderService.findPurchaseDTOs(query, PageRequest.of(page - 1, size, Sort.by("createDate").descending())));
	}
	
	@GetMapping(value = "/orders", params = {"query", "status"})
	public ResponseEntity<?> get(
			@RequestParam(name = "query") String query,
			@RequestParam(name = "status") OrderStatus orderStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(orderService.findPurchaseDTOs(query, orderStatus, PageRequest.of(page - 1, size, Sort.by("createDate").descending())));
	}
	// @formatter:on

	@GetMapping(value = "/orders/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok(orderService.findPurchaseInfoDTOById(id));
	}

	@PutMapping(value = "/orders/{id}/order-status")
	public ResponseEntity<?> updateOrderStatus(@PathVariable(name = "id") String id, @RequestBody JsonNode data,
			AccountAuthentication authentication) {
		orderService.updateOrderStatus(id, OrderStatus.valueOf(data.get("orderStatus").asText()),
				data.get("description").asText(), authentication);
		return ResponseEntity.noContent().build();
	}

}
