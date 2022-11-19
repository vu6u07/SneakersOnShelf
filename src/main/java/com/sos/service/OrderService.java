package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.entity.Order;
import com.sos.security.AccountAuthentication;

public interface OrderService extends CrudService<Order, String> {

	PurchaseInfoDTO findPurchaseInfoDTOById(String id);

	Page<PurchaseDTO> findPurchaseDTOs(Pageable pageable);

	Page<PurchaseDTO> findPurchaseDTOs(OrderStatus orderStatus, Pageable pageable);

	Page<PurchaseDTO> findPurchaseDTOs(String query, PageRequest pageable);

	Page<PurchaseDTO> findPurchaseDTOs(String query, OrderStatus orderStatus, PageRequest pageable);

	void updateOrderStatus(String id, OrderStatus orderStatus, String description,
			AccountAuthentication authentication);

}
