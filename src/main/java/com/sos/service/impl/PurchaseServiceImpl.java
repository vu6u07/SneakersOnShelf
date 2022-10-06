package com.sos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.CustomerInfoRepository;
import com.sos.repository.DeliveryRepository;
import com.sos.repository.OrderRepository;
import com.sos.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	private CustomerInfoRepository customerInfoRepository;

	@Override
	public PurchaseInfoDTO findPurchaseDTO(int id, String userTokenQuery) {
		PurchaseInfoDTO purchaseDTO = orderRepository.findPurchaseInfoDTO(id, userTokenQuery, OrderStatus.TEMPORARY)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
		purchaseDTO.setItems(orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId()));
		purchaseDTO.setDelivery(deliveryRepository.findByOrderId(purchaseDTO.getId()));
		purchaseDTO.setCustomerInfo(customerInfoRepository.findCustomerInfoFromOrder(purchaseDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin khách hàng")));
		return purchaseDTO;
	}

}
