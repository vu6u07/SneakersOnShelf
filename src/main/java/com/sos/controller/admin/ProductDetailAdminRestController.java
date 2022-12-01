package com.sos.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.service.ProductDetailService;

@RestController
@RequestMapping(value = "/admin/v1")
public class ProductDetailAdminRestController {

	@Autowired
	private ProductDetailService productDetailService;

	@PostMapping(value = "/products/{id}/product-details")
	public ResponseEntity<?> post(@PathVariable(name = "id") int id, @RequestBody JsonNode data) {
		productDetailService.addSize(id, data.get("size").asText(), data.get("quantity").asInt());
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/product-details/{id}/quantity")
	public ResponseEntity<?> changeQuantity(@PathVariable(name = "id") int id, @RequestBody int quantity) {
		productDetailService.changeProductDetailQuantity(id, quantity);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/product-details/{id}/status")
	public ResponseEntity<?> changeQuantity(@PathVariable(name = "id") int id, @RequestBody ActiveStatus activeStatus) {
		productDetailService.changeProductDetailStatus(id, activeStatus);
		return ResponseEntity.noContent().build();
	}

}
