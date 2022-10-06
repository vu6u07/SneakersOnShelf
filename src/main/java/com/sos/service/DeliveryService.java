package com.sos.service;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.entity.Delivery;

public interface DeliveryService extends CrudService<Delivery, Integer> {

	ResponseEntity<?> getAllProvinces();

	ResponseEntity<?> getAllDistrictsByProvinceId(int provinceId);

	ResponseEntity<?> getAllWardsByDistrictId(int districtId);

	ResponseEntity<?> getFeeAndExpectedTime(int orderId, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException;

	long getDeliveryFee(int orderId, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException;

}
