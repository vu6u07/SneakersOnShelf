package com.sos.controller.admin;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.common.SorterConstant.OrderSorter;
import com.sos.entity.CustomerInfo;
import com.sos.entity.OrderItem;
import com.sos.security.AccountAuthentication;
import com.sos.service.OrderService;
import com.sos.service.util.DateUtil;
import com.sos.service.util.ValidationUtil;

@RestController
@RequestMapping(value = "/admin/v1")
public class OrderAdminController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Validator validator;

	// @formatter:off
	@GetMapping(value = "/orders")
	public ResponseEntity<?> get(@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "method", required = false) SaleMethod saleMethod,
			@RequestParam(name = "status", required = false) OrderStatus orderStatus,
			@RequestParam(name = "from-date", required = false) String fromDateString,
			@RequestParam(name = "to-date", required = false) String toDateString,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sort", defaultValue = "date_desc") OrderSorter sorter) throws ParseException {
		Date fromDate = StringUtils.hasText(fromDateString) ? DateUtil.parse(fromDateString) : null;
		Date toDate = StringUtils.hasText(toDateString) ? DateUtil.getTomorrow(DateUtil.parse(toDateString)) : null;

		return ResponseEntity.ok(orderService.findAllPurchaseDTOs(query, saleMethod, orderStatus, fromDate, toDate,
				PageRequest.of(page - 1, size, sorter.getSort())));
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

	@PutMapping(value = "/orders/{id}/order-address")
	public ResponseEntity<?> updateOrderAddress(@PathVariable(name = "id") String id, @RequestBody JsonNode data,
			AccountAuthentication authentication) throws JsonProcessingException, IllegalArgumentException {
		CustomerInfo customerInfo = mapper.treeToValue(data.get("customerInfo"), CustomerInfo.class);
		Set<ConstraintViolation<CustomerInfo>> violations = validator.validate(customerInfo);
		if (!violations.isEmpty()) {
			String errorsMsg = violations.stream().map(ConstraintViolation<CustomerInfo>::getMessage)
					.collect(Collectors.joining(", "));
			throw new ValidationException(errorsMsg);
		}
		ValidationUtil.validatePhone(customerInfo.getPhone());
		orderService.updateOrderAddress(id, customerInfo, data.get("description").asText(), authentication);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/orders/{id}/order-items")
	public ResponseEntity<?> addOrderItemQuantity(@PathVariable(name = "id") String id,
			@RequestBody OrderItem orderItem, AccountAuthentication authentication)
			throws JsonProcessingException, IllegalArgumentException {
		orderService.addOrderItem(id, orderItem, authentication);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/order-items/{id}")
	public ResponseEntity<?> updateOrderItemQuantity(@PathVariable(name = "id") int id, @RequestBody JsonNode data,
			AccountAuthentication authentication) throws JsonProcessingException, IllegalArgumentException {
		orderService.updateOrderItemQuantity(id, data.get("quantity").asInt(), data.get("description").asText(),
				authentication);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/order-items/{id}")
	public ResponseEntity<?> deleteOrderItem(@PathVariable(name = "id") int id, @RequestParam String description,
			AccountAuthentication authentication) throws JsonProcessingException, IllegalArgumentException {
		orderService.deleteOrderItem(id, description, authentication);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/order-items/{id}/reverse")
	public ResponseEntity<?> reverseOrderItem(@PathVariable(name = "id") int id, @RequestBody JsonNode data,
			AccountAuthentication authentication) throws JsonProcessingException, IllegalArgumentException {
		orderService.reverseOrderItem(id, data.get("quantity").asInt(), data.get("surchange").asLong(),
				data.get("description").asText(), authentication);
		return ResponseEntity.noContent().build();
	}

}
