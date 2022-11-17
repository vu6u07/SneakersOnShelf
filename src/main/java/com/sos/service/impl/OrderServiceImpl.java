package com.sos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.OrderTimelineType;
import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.entity.Account;
import com.sos.entity.Order;
import com.sos.entity.OrderTimeline;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.OrderRepository;
import com.sos.repository.OrderTimelineRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderTimelineRepository orderTimelineRepository;

	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Order> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order save(Order entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<PurchaseDTO> findPurchaseDTOs(Pageable pageable) {
		return orderRepository.findAllPurchaseDTOs(pageable);
	}

	@Override
	public Page<PurchaseDTO> findPurchaseDTOs(OrderStatus orderStatus, Pageable pageable) {
		return orderRepository.findAllPurchaseDTOs(orderStatus, pageable);
	}

	@Override
	public Page<PurchaseDTO> findPurchaseDTOs(String query, PageRequest pageable) {
		return orderRepository.findAllPurchaseDTOs("%".concat(query).concat("%"), pageable);
	}
	
	@Override
	public Page<PurchaseDTO> findPurchaseDTOs(String query, OrderStatus orderStatus, PageRequest pageable) {
		return orderRepository.findAllPurchaseDTOs("%".concat(query).concat("%"), orderStatus, pageable);
	}

	@Override
	public PurchaseInfoDTO findPurchaseInfoDTOById(String id) {
		PurchaseInfoDTO purchaseDTO = orderRepository.findPurchaseInfoDTO(id)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng."));
		purchaseDTO.setItems(orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId()));
		purchaseDTO.setTimelines(orderTimelineRepository.findOrderTimelineDTOsByOrderId(purchaseDTO.getId()));
		return purchaseDTO;
	}

	@Transactional
	@Override
	public void updateOrderStatus(String id, OrderStatus orderStatus, String description,
			AccountAuthentication authentication) {
		PurchaseDTO purchaseDTO = orderRepository.findPurchaseDTOById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng."));
		OrderTimeline orderTimeline = new OrderTimeline();
		orderTimeline.setCreatedDate(new Date());
		orderTimeline.setStaff(new Account(authentication.getId()));
		orderTimeline.setDescription(description);
		orderTimeline.setOrder(new Order(purchaseDTO.getId()));

		switch (orderStatus) {
		case PENDING:
			throw new ValidationException();
		case APPROVED:
			if (purchaseDTO.getOrderStatus() != OrderStatus.SHIPPING
					&& purchaseDTO.getOrderStatus() != OrderStatus.CONFIRMED) {
				throw new ValidationException();
			}
			orderTimeline.setOrderTimelineType(OrderTimelineType.APPROVED);
			break;
		case SHIPPING:
			if (purchaseDTO.getOrderStatus() != OrderStatus.CONFIRMED) {
				throw new ValidationException();
			}
			orderTimeline.setOrderTimelineType(OrderTimelineType.SHIPPING);
			break;
		case CANCELLED:
			if (purchaseDTO.getOrderStatus() == OrderStatus.APPROVED
					|| purchaseDTO.getOrderStatus() == OrderStatus.REVERSE
					|| purchaseDTO.getOrderStatus() == OrderStatus.CANCELLED) {
				throw new ValidationException();
			}
			orderTimeline.setOrderTimelineType(OrderTimelineType.CANCELLED);
			break;
		case CONFIRMED:
			if (purchaseDTO.getOrderStatus() != OrderStatus.PENDING) {
				throw new ValidationException();
			}
			orderTimeline.setOrderTimelineType(OrderTimelineType.CONFIRMED);
			break;
		case REVERSE:
			if (purchaseDTO.getOrderStatus() != OrderStatus.APPROVED) {
				throw new ValidationException();
			}
			orderTimeline.setOrderTimelineType(OrderTimelineType.EDITED);
			break;
		default:
			break;
		}
		orderTimelineRepository.save(orderTimeline);
		if (orderRepository.updateOrderStatus(id, orderStatus) != 1) {
			throw new ResourceNotFoundException("Không tìm thấy đơn hàng.");
		}
	}

}
