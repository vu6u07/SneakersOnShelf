package com.sos.service;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DeliveryService {

	ResponseEntity<?> getAllProvinces();

	ResponseEntity<?> getAllDistrictsByProvinceId(int provinceId);

	ResponseEntity<?> getAllWardsByDistrictId(int districtId);

	ResponseEntity<?> getFeeAndExpectedTime(int cartId, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException;

	long getDeliveryFee(int cartId, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException;

	long getDeliveryFee(long insuranceValue, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException;

}
