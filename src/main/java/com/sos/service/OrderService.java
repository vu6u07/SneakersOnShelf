package com.sos.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;
import com.sos.entity.OrderItem;
import com.sos.security.AccountAuthentication;

public interface OrderService extends CrudService<Order, String> {

	PurchaseInfoDTO findPurchaseInfoDTOById(String id);

	Page<PurchaseDTO> findAllPurchaseDTOs(String query, SaleMethod saleMethod, OrderStatus orderStatus, Date fromDate, Date toDate, PageRequest pageable);

	void updateOrderStatus(String id, OrderStatus orderStatus, String description,
			AccountAuthentication authentication);

	void updateOrderAddress(String id, CustomerInfo customerInfo, String description,
			AccountAuthentication authentication) throws JsonMappingException, JsonProcessingException;

	void addOrderItem(String id, OrderItem orderItem, AccountAuthentication authentication) throws JsonMappingException, JsonProcessingException;
	
	void updateOrderItemQuantity(int id, int quantity, String description, AccountAuthentication authentication) throws JsonMappingException, JsonProcessingException;
	
	void deleteOrderItem(int id, String description, AccountAuthentication authentication) throws JsonMappingException, JsonProcessingException;
	
}
