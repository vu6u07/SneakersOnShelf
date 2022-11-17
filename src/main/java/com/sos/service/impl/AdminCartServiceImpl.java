package com.sos.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.CartStatus;
import com.sos.common.ApplicationConstant.OrderItemStatus;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.OrderTimelineType;
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.dto.CartDTO;
import com.sos.dto.CartReportDTO;
import com.sos.dto.EmailRequest;
import com.sos.entity.Account;
import com.sos.entity.Cart;
import com.sos.entity.CartItem;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;
import com.sos.entity.OrderItem;
import com.sos.entity.OrderTimeline;
import com.sos.entity.ProductDetail;
import com.sos.entity.Voucher;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.CartItemRepository;
import com.sos.repository.CartRepository;
import com.sos.repository.OrderItemRepository;
import com.sos.repository.OrderRepository;
import com.sos.repository.OrderTimelineRepository;
import com.sos.repository.ProductDetailRepository;
import com.sos.repository.VoucherRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.AdminCartService;
import com.sos.service.DeliveryService;
import com.sos.service.EmailService;
import com.sos.service.util.CartUtils;
import com.sos.service.util.EmailUtil;

@Service("AdminCartServiceImpl")
public class AdminCartServiceImpl implements AdminCartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderTimelineRepository orderTimelineRepository;

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private EmailService emailService;

	@Override
	public Page<CartReportDTO> findCartReportDTO(AccountAuthentication authentication, CartStatus cartStatus,
			Pageable pageable) {
		return cartRepository.findCartReportDTO(authentication.getId(), cartStatus, pageable);
	}

	@Override
	public Page<CartReportDTO> findCartReportDTO(AccountAuthentication authentication, int id, Pageable pageable) {
		return cartRepository.findCartReportDTO(authentication.getId(), id, pageable);
	}

	@Override
	public CartDTO createCart(AccountAuthentication authentication) {
		Cart cart = new Cart();
		Date date = new Date();
		cart.setAccount(new Account(authentication.getId()));
		cart.setCartStatus(CartStatus.PENDING);
		cart.setCreateDate(date);
		cart.setUpdateDate(date);
		cartRepository.save(cart);
		return new CartDTO(cart.getId());
	}

	@Override
	public void addToCart(int id, int productId, int quantity, AccountAuthentication authentication) {
		Cart cart = cartRepository.findCartId(id, CartStatus.PENDING, authentication.getId())
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

	@Override
	public CartDTO getCartDTOById(int id, AccountAuthentication authentication) {
		CartDTO rs = cartRepository.findCartDTO(id, CartStatus.PENDING, authentication.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		rs.setItems(cartRepository.findAllCartItemDTOByCartId(rs.getId()));
		return rs;
	}

	@Override
	public void changeCartItemQuantity(int id, int quantity, AccountAuthentication authentication) {
		CartItem cartItem = cartItemRepository.findCartItemId(id, CartStatus.PENDING, authentication.getId())
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
	public void deleteCartItem(int cartItemId, AccountAuthentication authentication) {
		CartItem cartItem = cartItemRepository.findCartItemId(cartItemId, CartStatus.PENDING, authentication.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		cartItemRepository.deleteById(cartItem.getId());
	}

	@Override
	public void deleteAllCartItem(int cartId, AccountAuthentication authentication) {
		Cart cart = cartRepository.findCartId(cartId, CartStatus.PENDING, authentication.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng."));
		cartItemRepository.deleteCartItemByCartId(cart.getId());
	}

	@Transactional
	@Override
	public Order submitCart(int id, CustomerInfo customerInfo, String email, SaleMethod saleMethod, Voucher voucher,
			AccountAuthentication authentication) throws JsonMappingException, JsonProcessingException {
		Cart cart = cartRepository.findCartId(id, CartStatus.PENDING, authentication.getId())
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
				throw new ValidationException(String.format("Sản phẩm %s [Cỡ %s] số lượng không đủ đáp ứng",
						cartItem.getProductDetail().getProduct().getName(), cartItem.getProductDetail().getSize()));
			}

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProductDetail(cartItem.getProductDetail());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setOrderItemStatus(OrderItemStatus.APPROVED);
			orderItem.setPrice(cartItem.getProductDetail().getProduct().getSellPrice());
			productDetailRepository.decreaseProductDetailQuantity(orderItem.getProductDetail().getId(),
					orderItem.getQuantity());
			total += orderItem.getPrice() * orderItem.getQuantity();
			orderItems.add(orderItem);
		}

		order.setId(CartUtils.generateOrderId(now, id));
		order.setSaleMethod(saleMethod);
		order.setToken(cart.getToken());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setTotal(total);
		order.setAccount(customerInfo.getAccount());

		if (voucher != null) {
			Voucher selectedVoucher = voucherRepository
					.findAvailableVoucherById(voucher.getId(), VoucherStatus.ACTIVE, now)
					.orElseThrow(() -> new ValidationException("Mã giảm giá không hợp lệ."));
			if (order.getTotal() < selectedVoucher.getRequiredValue()) {
				throw new ValidationException("Mã giảm giá không hợp lệ.");
			}

			long discount = 0;

			switch (selectedVoucher.getVoucherType()) {
			case PERCENT:
				if (selectedVoucher.getAmount() <= 0 || selectedVoucher.getAmount() > 100) {
					throw new ValidationException("Mã giảm giá không hợp lệ.");
				}
				discount = order.getTotal() * selectedVoucher.getAmount() / 100;
				break;
			case DISCOUNT:
				discount = selectedVoucher.getAmount();
				break;
			default:
				throw new ValidationException("Mã giảm giá không hợp lệ.");
			}

			if (selectedVoucher.getMaxValue() > 0 && discount > selectedVoucher.getMaxValue()) {
				discount = selectedVoucher.getMaxValue();
			}
			order.setVoucher(selectedVoucher);
			order.setDiscount(discount <= order.getTotal() ? discount : order.getTotal());

			if (voucherRepository.decreateVoucherQuantity(selectedVoucher.getId()) != 1) {
				throw new ValidationException("Mã giảm giá không hợp lệ.");
			}
		}

		if (saleMethod == SaleMethod.DELIVERY) {
			order.setFee(deliveryService.getDeliveryFee(order.getTotal() + order.getSurcharge() - order.getDiscount(),
					customerInfo.getDistrictId(), customerInfo.getWardCode()));
			order.setProvinceId(customerInfo.getProvinceId());
			order.setDistrictId(customerInfo.getDistrictId());
			order.setWardCode(customerInfo.getWardCode());
			order.setAddress(String.format("%s, %s, %s", customerInfo.getWardName(), customerInfo.getDistrictName(),
					customerInfo.getProvinceName()));
			order.setDetailedAddress(customerInfo.getAddress());
		}

		order.setFullname(customerInfo.getFullname());
		order.setEmail(email);
		order.setPhone(customerInfo.getPhone());
		order.setCreateDate(now);
		
		orderRepository.save(order);
		orderItemRepository.saveAll(orderItems);

		OrderTimeline orderTimeline = new OrderTimeline();
		orderTimeline.setCreatedDate(now);
		orderTimeline.setOrder(order);
		orderTimeline.setOrderTimelineType(OrderTimelineType.CREATED);
		orderTimeline.setStaff(new Account(authentication.getId()));
		orderTimelineRepository.save(orderTimeline);

		if (cartRepository.updateCartStatus(cart.getId(), CartStatus.APPROVED, new Date(),
				authentication.getId()) == 0) {
			throw new ResourceNotFoundException("Không tìm thấy giỏ hàng");
		}

		if (order.getEmail() != null) {
			CompletableFuture.runAsync(() -> {
				try {
					emailService.sendEmail(new EmailRequest(new String[] { order.getEmail() }, null, null,
							EmailUtil.getNewOrderEmailSubject(order.getId()), EmailUtil.getNewOrderEmailContent(order),
							true));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			});
		}

		return order;
	}

	@Transactional
	@Override
	public void deleteCart(int id, AccountAuthentication authentication) {
		if (cartRepository.updateCartStatus(id, CartStatus.CANCELLED, new Date(), authentication.getId()) != 1) {
			throw new ResourceNotFoundException("Có lỗi xảy ra, hãy thử lại sau.");
		}
	}

}
