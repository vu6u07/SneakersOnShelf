package com.sos.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.entity.Rate;
import com.sos.security.AccountAuthentication;
import com.sos.service.RateService;

@RestController
@RequestMapping(value = "/api/v1")
public class RateRestController {

	@Autowired
	private RateService rateService;

	@GetMapping(value = "/products/{id}/rates")
	public ResponseEntity<?> getByProductDetailId(@PathVariable(name = "id") int id,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(rateService.findByProductDetailId(id, PageRequest.of(page - 1, size)));
	}

	@PostMapping(value = "/order-items/{id}/rates")
	public ResponseEntity<?> post(@PathVariable(name = "id") int id, @RequestBody Rate rate,
			AccountAuthentication authentication) {
		if (rate.getScore() < 1 || rate.getScore() > 5) {
			throw new ValidationException("Đánh giá không hợp lệ.");
		}
		return ResponseEntity.ok(rateService.save(id, rate, authentication));
	}

}
