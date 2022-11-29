package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.service.BrandService;

@RestController
@RequestMapping(value = "/api/v1/brands")
public class BrandRestController {

	@Autowired
	private BrandService brandService;

	@GetMapping
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(brandService.findAll(ActiveStatus.ACTIVE));
	}

}
