package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.service.DeliveryService;

@RestController
@RequestMapping(value = "/content/v1/delivery")
public class DeliveryPartnerRestController {

	@Autowired
	private DeliveryService deliveryService;

	@GetMapping(value = "/provinces")
	public ResponseEntity<?> getProvinces() {
		return deliveryService.getAllProvinces();
	}

	@GetMapping(value = "/provinces/{id}/districts")
	public ResponseEntity<?> getDistrict(@PathVariable(name = "id") int provinceId) {
		return deliveryService.getAllDistrictsByProvinceId(provinceId);
	}

	@GetMapping(value = "/districts/{id}/wards")
	public ResponseEntity<?> getWards(@PathVariable(name = "id") int districtId) {
		return deliveryService.getAllWardsByDistrictId(districtId);
	}

	// @formatter:off
	@GetMapping(value = "/{id}/calculate")
	public ResponseEntity<?> getWards(
			@PathVariable(name = "id") int cartId,
			@RequestParam(name = "district_id") int districtId,
			@RequestParam(name = "ward_code") String wardCode)
			throws JsonMappingException, JsonProcessingException {
		return deliveryService.getFeeAndExpectedTime(cartId, districtId, wardCode);
	}
	// @formatter:on

}
