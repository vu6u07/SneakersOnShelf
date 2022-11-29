package com.sos.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.sos.service.ProductDetailService;

@RestController
@RequestMapping(value = "/admin/v1")
public class ProductDetailAdminRestController {

	@Autowired
	private ProductDetailService productDetailService;

	@PostMapping(value = "/{id}/product-details")
	public ResponseEntity<?> post(@RequestParam(name = "id") int productId, JsonNode data) {
		productDetailService.addSize(productId, data.get("size").asText(), data.get("quantity").asInt());
		return ResponseEntity.noContent().build();
	}

}
