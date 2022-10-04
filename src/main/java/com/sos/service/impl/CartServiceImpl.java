package com.sos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.dto.CartDTO;
import com.sos.entity.Order;
import com.sos.entity.OrderItem;
import com.sos.entity.ProductDetail;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.CartRepository;
import com.sos.repository.OrderItemRepository;
import com.sos.repository.OrderRepository;
import com.sos.repository.ProductDetailRepository;
import com.sos.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;

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
		CartDTO rs = cartRepository.findCartDTOByOrderIdAndUserTokenQuery(id, userTokenQuery)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		rs.setItems(cartRepository.findAllCartItemDTOByOrderId(id));
		return rs;
	}

	@Override
	public void addToCart(int id, int productId, String userTokenQuery) {
		Order order = cartRepository.findByOrderIdAndUserTokenQuery(id, userTokenQuery)
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

	private String generateUserTokenQuery() {
		return UUID.randomUUID().toString();
	}

}
