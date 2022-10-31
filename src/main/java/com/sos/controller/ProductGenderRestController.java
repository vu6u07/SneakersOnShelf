package com.sos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ProductGender;

@RestController
@RequestMapping(value = "/api/v1/product-genders")
public class ProductGenderRestController {

	@GetMapping
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(ProductGender.values());
	}

}
