package com.sos.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.CartStatus;
import com.sos.common.ApplicationConstant.CustomerInfoStatus;
import com.sos.common.ApplicationConstant.DeliveryPartner;
import com.sos.common.ApplicationConstant.DeliveryStatus;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.PaymentStatus;
import com.sos.dto.CartDTO;
import com.sos.dto.EmailRequest;
import com.sos.entity.Cart;
import com.sos.entity.CartItem;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Delivery;
import com.sos.entity.Order;
import com.sos.entity.OrderItem;
import com.sos.entity.ProductDetail;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.CartItemRepository;
import com.sos.repository.CartRepository;
import com.sos.repository.CustomerInfoRepository;
import com.sos.repository.OrderItemRepository;
import com.sos.repository.OrderRepository;
import com.sos.repository.ProductDetailRepository;
import com.sos.service.AnonymouseCartService;
import com.sos.service.DeliveryService;
import com.sos.service.EmailService;
import com.sos.service.util.EmailUtil;

@Service
public class AnonymouseCartServiceImpl implements AnonymouseCartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

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

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public CartDTO createAnonymousCart() {
		Date date = new Date();
		Cart cart = new Cart();
		cart.setCartStatus(CartStatus.PENDING);
		cart.setCreateDate(date);
		cart.setUpdateDate(date);
		cart.setToken(generateTokenQuery());
		cartRepository.save(cart);
		return new CartDTO(cart.getId(), cart.getToken(), new ArrayList<>());
	}

	@Override
	public CartDTO getCartDTO(int id, String token) {
		CartDTO rs = cartRepository.findCartDTO(id, CartStatus.PENDING, token)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		rs.setItems(cartRepository.findAllCartItemDTOByCartId(id));
		return rs;
	}

	@Override
	public void addToCart(int id, int productId, int quantity, String token) {
		Cart cart = cartRepository.findCartId(id, CartStatus.PENDING, token)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));

		ProductDetail productDetail = productDetailRepository.findByProductDetailId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm."));

		if (quantity < 1) {
			throw new ValidationException("Số lượng đặt hàng không hợp lệ.");
		}

		CartItem cartItem = cartItemRepository.findByCartIdAndProductDetailId(cart.getId(), productDetail.getId())
				.orElseGet(() -> new CartItem(cart, productDetail, 0));
		cartItem.setQuantity(cartItem.getQuantity() + quantity);
		if (cartItem.getQuantity() > productDetail.getQuantity()) {
			throw new ValidationException("Sản phẩm tạm hết hàng.");
		}

		cartItemRepository.save(cartItem);
	}

	@Transactional
	@Override
	public void changeCartItemQuantity(int id, int quantity, String token) {
		CartItem cartItem = cartItemRepository.findCartItemId(id, CartStatus.PENDING, token)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		ProductDetail productDetail = productDetailRepository.findByCartItemId(cartItem.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm."));

		if (productDetail.getQuantity() < quantity) {
			throw new ValidationException(String.format("Sản phẩm chỉ còn lại %s chiếc.", productDetail.getQuantity()));
		}

		if (cartItemRepository.changeCartItemQuantity(cartItem.getId(), quantity) == 0) {
			throw new ValidationException("Có lỗi xảy ra, hãy thử lại sau.");
		}
	}

	@Override
	public void deleteCartItem(int id, String token) {
		CartItem cartItem = cartItemRepository.findCartItemId(id, CartStatus.PENDING, token)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		cartItemRepository.deleteById(cartItem.getId());
	}

	@Transactional
	@Override
	public void deleteAllCartItem(int cartId, String token) {
		Cart cart = cartRepository.findCartId(cartId, CartStatus.PENDING, token)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		cartItemRepository.deleteCartItemByCartId(cart.getId());
	}

	@Transactional
	@Override
	public synchronized Order submitCart(int id, String token, CustomerInfo customerInfo, PaymentMethod paymentMethod,
			String email) throws JsonMappingException, JsonProcessingException {
		Cart cart = cartRepository.findCart(id, CartStatus.PENDING, token)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));

		List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cart.getId());

		if (cartItems.size() < 1) {
			throw new ValidationException("Không có sản phẩm nào trong giỏ.");
		}

		Date now = new Date();
		long total = 0l;

		Order order = new Order();
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem cartItem : cartItems) {
			if (cartItem.getQuantity() < 1) {
				throw new ValidationException("Số lượng đặt hàng không hợp lệ");
			}

			if (cartItem.getQuantity() > cartItem.getProductDetail().getQuantity()) {
				throw new ResourceNotFoundException(String.format("Sản phẩm %s [Cỡ %s] số lượng không đủ đáp ứng",
						cartItem.getProductDetail().getProduct().getName(), cartItem.getProductDetail().getSize()));
			}

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProductDetail(cartItem.getProductDetail());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProductDetail().getProduct().getSellPrice());
			productDetailRepository.decreaseProductDetailQuantity(orderItem.getProductDetail().getId(),
					orderItem.getQuantity());
			total += orderItem.getPrice() * orderItem.getQuantity();
			orderItems.add(orderItem);
		}

		customerInfo.setId(0);
		customerInfo.setCustomerInfoStatus(CustomerInfoStatus.ACTIVE);
		customerInfo.setCreateDate(now);
		customerInfo.setUpdateDate(now);
		customerInfoRepository.save(customerInfo);
		order.setId(UUID.randomUUID());
		order.setToken(cart.getToken());
		order.setCustomerInfo(customerInfo);
		order.setTotal(total);

		Delivery delivery = new Delivery("123456789",
				deliveryService.getDeliveryFee(order.getTotal() + order.getSurcharge() - order.getDiscount(),
						customerInfo.getDistrictId(), customerInfo.getWardCode()),
				String.format("%s, %s, %s, %s", customerInfo.getAddress(), customerInfo.getWardName(),
						customerInfo.getDistrictName(), customerInfo.getProvinceName()),
				DeliveryPartner.GHN, DeliveryStatus.PENDING, null, now, now);

		deliveryService.save(delivery);
		order.setDelivery(delivery);
		order.setOrderStatus(OrderStatus.PENDING);
		order.setPaymentMethod(paymentMethod);
		order.setPaymentStatus(PaymentStatus.PENDING);
		order.setCreateDate(now);
		order.setUpdateDate(now);
		order.setEmail(email);

		Order created = orderRepository.save(order);

		orderItemRepository.saveAll(orderItems);

		if (cartRepository.updateCartStatus(cart.getId(), CartStatus.APPROVED) == 0) {
			throw new ResourceNotFoundException("Không tìm thấy giỏ hàng");
		}

		if (email != null) {
			try {
				emailService.sendEmail(new EmailRequest(new String[] { email }, null, null,
						EmailUtil.getNewOrderEmailSubject(order.getId().toString()),
						EmailUtil.getNewOrderEmailContent(order), true));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return created;
	}

	private String generateTokenQuery() {
		return passwordEncoder.encode(UUID.randomUUID().toString()).replaceAll("/", "_");
	}

}
