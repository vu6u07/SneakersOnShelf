package com.sos.service.impl;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sos.repository.CartRepository;
import com.sos.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${delivery.partner.ghn.token}")
	private String token;

	@Value("${delivery.partner.ghn.shop.id}")
	private int ghnShopId;

	@Value("${delivery.partner.ghn.district.id}")
	private int ghnShopDistrictId;

	@Value("${delivery.partner.ghn.ward.code}")
	private String ghnShopWardCode;

	private HttpEntity<Object> httpEntity;

	private HttpHeaders headers;

	@PostConstruct
	private void init() {
		headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Token", token);
		headers.set("ShopId", String.valueOf(ghnShopId));
		httpEntity = new HttpEntity<>(headers);
	}

	@Override
	public ResponseEntity<?> getAllProvinces() {
		return restTemplate.exchange("https://online-gateway.ghn.vn/shiip/public-api/master-data/province",
				HttpMethod.GET, httpEntity, Object.class);
	}

	@Override
	public ResponseEntity<?> getAllDistrictsByProvinceId(int provinceId) {
		return restTemplate.exchange(
				"https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + provinceId,
				HttpMethod.GET, httpEntity, Object.class);
	}

	@Override
	public ResponseEntity<?> getAllWardsByDistrictId(int districtId) {
		return restTemplate.exchange(
				"https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + districtId,
				HttpMethod.GET, httpEntity, Object.class);
	}

	@Override
	public ResponseEntity<?> getFeeAndExpectedTime(int cartId, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException {
		int serviceId = getAvailableServiceId(ghnShopId, ghnShopDistrictId, districtId);
		long insuranceValue = cartRepository.getTotal(cartId);

//		Optional<Voucher> voucher = voucherRepository.getVoucherAmount(cartId);
//		if (voucher.isPresent()) {
//			insuranceValue -= voucher.get().getAmount();
//		}

		long fee = getFee(ghnShopDistrictId, serviceId, districtId, wardCode, 20, 11, 31, 1000, insuranceValue);
		long leadtime = getLeadTime(ghnShopDistrictId, ghnShopWardCode, districtId, wardCode, serviceId);

		ObjectNode rs = objectMapper.createObjectNode();
		rs.put("fee", fee);
		rs.put("leadtime", leadtime);
		return ResponseEntity.ok(rs);
	}

	@Override
	public long getDeliveryFee(int cartId, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException {
		int serviceId = getAvailableServiceId(ghnShopId, ghnShopDistrictId, districtId);
		long insuranceValue = cartRepository.getTotal(cartId);

//		Optional<Voucher> voucher = voucherRepository.getVoucherAmount();
//		if (voucher.isPresent()) {
//			insuranceValue -= voucher.get().getAmount();
//		}

		return getFee(ghnShopDistrictId, serviceId, districtId, wardCode, 20, 11, 31, 1000, insuranceValue);
	}

	@Override
	public long getDeliveryFee(long insuranceValue, int districtId, String wardCode)
			throws JsonMappingException, JsonProcessingException {
		int serviceId = getAvailableServiceId(ghnShopId, ghnShopDistrictId, districtId);
		return getFee(ghnShopDistrictId, serviceId, districtId, wardCode, 20, 11, 31, 1000, insuranceValue);
	}

	private long getLeadTime(int fromDistrictId, String fromWardCode, int toDistrictId, String toWardCode,
			int serviceId) {
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("from_district_id", fromDistrictId);
		objectNode.put("from_ward_code", fromWardCode);
		objectNode.put("to_district_id", toDistrictId);
		objectNode.put("to_ward_code", toWardCode);
		objectNode.put("service_id", serviceId);

		JsonNode response = restTemplate
				.exchange("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/leadtime", HttpMethod.POST,
						new HttpEntity<String>(objectNode.toPrettyString(), headers), JsonNode.class)
				.getBody();

		return response.get("data").get("leadtime").asLong();
	}

	public long getFee(int fromDistrictId, int serviceId, int toDistrictId, String toWardCode, int width, int height,
			int length, int weight, long insuranceValue) {
		ObjectNode objectNode = objectMapper.createObjectNode();

		objectNode.put("from_district_id", fromDistrictId);
		objectNode.put("service_id", serviceId);
		objectNode.put("to_district_id", toDistrictId);
		objectNode.put("to_ward_code", toWardCode);
		objectNode.put("height", height);
		objectNode.put("length", length);
		objectNode.put("weight", weight);
		objectNode.put("width", width);
		objectNode.put("insurance_value", insuranceValue);

		JsonNode response = restTemplate
				.exchange("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", HttpMethod.POST,
						new HttpEntity<String>(objectNode.toPrettyString(), headers), JsonNode.class)
				.getBody();

		return response.get("data").get("total").asLong();
	}

	private int getAvailableServiceId(int shopId, int fromDistrictId, int toDistrictId)
			throws JsonMappingException, JsonProcessingException {
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("shop_id", shopId);
		objectNode.put("from_district", fromDistrictId);
		objectNode.put("to_district", toDistrictId);

		JsonNode node = restTemplate
				.exchange("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services",
						HttpMethod.POST, new HttpEntity<String>(objectNode.toPrettyString(), headers), JsonNode.class)
				.getBody();
		return node.get("data").get(0).get("service_id").asInt();
	}

}
