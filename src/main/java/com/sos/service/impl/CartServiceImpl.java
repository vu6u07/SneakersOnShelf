package com.sos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.DeliveryPartner;
import com.sos.common.ApplicationConstant.DeliveryStatus;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.PaymentStatus;
import com.sos.dto.CartDTO;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Delivery;
import com.sos.entity.Order;
import com.sos.entity.OrderItem;
import com.sos.entity.ProductDetail;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.CustomerInfoRepository;
import com.sos.repository.OrderItemRepository;
import com.sos.repository.OrderRepository;
import com.sos.repository.ProductDetailRepository;
import com.sos.service.CartService;
import com.sos.service.DeliveryService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private CustomerInfoRepository customerInfoRepository;
	
	@Autowired
	private DeliveryService deliveryService;

	@Override
	public CartDTO getCartDTO() {
		Order order = new Order();
		order.setOrderStatus(OrderStatus.TEMPORARY);
		order.setUserTokenQuery(generateUserTokenQuery());
		order.setCreateDate(new Date());
		orderRepository.save(order);
		return new CartDTO(order.getId(), order.getUserTokenQuery(), new ArrayList<>());
	}

	@Override
	public CartDTO getCartDTO(int id) {
		return null;
	}

	@Override
	public CartDTO getCartDTO(int id, String userTokenQuery) {
		CartDTO rs = orderRepository.findCartDTO(id, userTokenQuery, OrderStatus.TEMPORARY)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		rs.setItems(orderRepository.findAllCartItemDTO(id));
		return rs;
	}

	@Override
	public void addToCart(int id, int productId, String userTokenQuery) {
		Order order = orderRepository.findOrderId(id, userTokenQuery, OrderStatus.TEMPORARY)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));

		ProductDetail productDetail = productDetailRepository.findByProductDetailId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm."));

		if (productDetail.getQuantity() < 1) {
			throw new ResourceNotFoundException("Sản phẩm tạm hết hàng.");
		}

		OrderItem orderItem = orderItemRepository.findByOrderIdAndProductId(order.getId(), productId).orElseGet(() -> {
			OrderItem oi = new OrderItem();
			return oi;
		});
		orderItem.setQuantity(orderItem.getQuantity() + 1);
		orderItem.setOrder(order);
		orderItem.setProductDetail(productDetail);
		orderItemRepository.save(orderItem);
	}

	@Override
	public void deleteCartItem(int id, int cartId, String userTokenQuery) {
		OrderItem oi = orderItemRepository.findOrderItem(id, cartId, userTokenQuery, OrderStatus.TEMPORARY)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));
		orderItemRepository.deleteById(oi.getId());
	}

	@Transactional
	@Override
	public synchronized void submitCart(int id, String userTokenQuery, CustomerInfo customerInfo, PaymentMethod paymentMethod) throws JsonMappingException, JsonProcessingException {
		Order order = orderRepository.findOrder(id, userTokenQuery, OrderStatus.TEMPORARY)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));

		List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(order.getId());
		Date now = new Date();
		long total = 0l;

		for (OrderItem orderItem : orderItems) {
			if (orderItem.getQuantity() < 1) {
				throw new ValidationException("Số lượng đặt hàng không hợp lệ");
			}

			if (orderItem.getQuantity() > orderItem.getProductDetail().getQuantity()) {
				throw new ResourceNotFoundException(String.format("Sản phẩm %s [Cỡ %s] số lượng không đủ đáp ứng",
						orderItem.getProductDetail().getProduct().getName(), orderItem.getProductDetail().getSize()));
			}

			orderItem.setPrice(orderItem.getProductDetail().getProduct().getSellPrice());
			productDetailRepository.decreaseProductDetailQuantity(orderItem.getProductDetail().getId(),
					orderItem.getQuantity());
			orderItemRepository.updateOrderItemPrice(orderItem.getId(), orderItem.getPrice());
			total += orderItem.getPrice() * orderItem.getQuantity();
		}

		customerInfo.setCreateDate(now);
		customerInfo.setUpdateDate(now);
		customerInfoRepository.save(customerInfo);
		order.setCustomerInfo(customerInfo);
		order.setTotal(total);
		
		
		Delivery delivery = new Delivery("123456789", deliveryService.getDeliveryFee(order.getId(), customerInfo.getDistrictId(), customerInfo.getWardCode()), DeliveryPartner.GHN, DeliveryStatus.PENDING, null,
				now, now);
		deliveryService.save(delivery);
		order.setDelivery(delivery);
		order.setOrderStatus(OrderStatus.PENDING);
		order.setPaymentMethod(paymentMethod);
		order.setPaymentStatus(PaymentStatus.PENDING);
		order.setCreateDate(now);
		order.setUpdateDate(now);
		orderRepository.save(order);
	}

	private String generateUserTokenQuery() {
		return UUID.randomUUID().toString();
	}

}
