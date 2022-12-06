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
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.OrderItemStatus;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.OrderTimelineType;
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.dto.CartItemDTO;
import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.entity.Account;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;
import com.sos.entity.OrderItem;
import com.sos.entity.OrderTimeline;
import com.sos.entity.ProductDetail;
import com.sos.entity.Voucher;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.OrderItemRepository;
import com.sos.repository.OrderRepository;
import com.sos.repository.OrderTimelineRepository;
import com.sos.repository.ProductDetailRepository;
import com.sos.repository.TransactionRepository;
import com.sos.repository.VoucherRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.DeliveryService;
import com.sos.service.MemberOfferPolicyService;
import com.sos.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private MemberOfferPolicyService memberOfferPolicyService;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderTimelineRepository orderTimelineRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private VoucherRepository voucherRepository;

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
	public Page<PurchaseDTO> findAllPurchaseDTOs(String query, SaleMethod saleMethod, OrderStatus orderStatus,
			Date fromDate, Date toDate, PageRequest pageable) {
		return orderRepository.findAllPurchaseDTOs(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null,
				saleMethod, orderStatus, fromDate, toDate, pageable);
	}

	@Override
	public PurchaseInfoDTO findPurchaseInfoDTOById(String id) {
		PurchaseInfoDTO purchaseDTO = orderRepository.findPurchaseInfoDTO(id)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng."));
		purchaseDTO.setItems(orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId()));
		purchaseDTO.setTimelines(orderTimelineRepository.findOrderTimelineDTOsByOrderId(purchaseDTO.getId()));
		purchaseDTO.setTransactions(transactionRepository.findAllTransactionDTOByOrderId(purchaseDTO.getId()));
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

			List<CartItemDTO> items = orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId());
			for (CartItemDTO item : items) {
				if (productDetailRepository.increaseProductDetailQuantity(item.getProductDetailId(),
						item.getQuantity()) != 1) {
					throw new ResourceNotFoundException("Product not found");
				}
			}
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

		if (orderStatus == OrderStatus.APPROVED) {
			long point = (purchaseDTO.getTotal() / 1000);
			memberOfferPolicyService.rewardPoint(purchaseDTO.getId(), point);
		}
	}

	@Transactional
	@Override
	public void updateOrderAddress(String id, CustomerInfo customerInfo, String description,
			AccountAuthentication authentication) throws JsonMappingException, JsonProcessingException {
		Order order = orderRepository.findStagingOrder(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id : " + id));

		if (order.getOrderStatus() != OrderStatus.PENDING && order.getOrderStatus() != OrderStatus.CONFIRMED) {
			throw new ValidationException("Trạng thái đơn hàng không hợp lệ.");
		}

		if (order.getSaleMethod() != SaleMethod.DELIVERY) {
			throw new ValidationException("Loại đơn hàng không hợp lệ.");
		}

		long fee = deliveryService.getDeliveryFee(order.getTotal() + order.getSurcharge() - order.getDiscount(),
				customerInfo.getDistrictId(), customerInfo.getWardCode());

		if (orderRepository.updateOrderAddress(
				id, fee, customerInfo.getFullname(), customerInfo.getPhone(), customerInfo.getProvinceId(),
				customerInfo.getDistrictId(), customerInfo.getWardCode(), String.format("%s, %s, %s",
						customerInfo.getWardName(), customerInfo.getDistrictName(), customerInfo.getProvinceName()),
				customerInfo.getAddress()) != 1) {
			throw new ValidationException("Có lỗi xảy ra, hãy thử lại sau.");
		}

		OrderTimeline orderTimeline = new OrderTimeline();
		orderTimeline.setCreatedDate(new Date());
		orderTimeline.setOrder(order);
		orderTimeline.setOrderTimelineType(OrderTimelineType.EDITED);
		orderTimeline.setDescription("Cập nhật địa chỉ đơn hàng. ".concat(description));
		orderTimeline.setStaff(new Account(authentication.getId()));
		orderTimelineRepository.save(orderTimeline);
	}

	@Transactional
	@Override
	public void addOrderItem(String id, OrderItem orderItem, AccountAuthentication authentication)
			throws JsonMappingException, JsonProcessingException {
		if (orderItem.getProductDetail() == null || orderItem.getQuantity() <= 0) {
			throw new ValidationException();
		}

		Order order = orderRepository.findStagingOrder(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order Item not found with id : " + id));
		if (order.getOrderStatus() != OrderStatus.PENDING && order.getOrderStatus() != OrderStatus.CONFIRMED) {
			throw new ValidationException("Trạng thái đơn hàng không hợp lệ.");
		}

		ProductDetail productDetail = productDetailRepository
				.findProductDetailById(orderItem.getProductDetail().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		if (productDetail.getQuantity() < orderItem.getQuantity()) {
			throw new ValidationException(String.format("Sản phẩm %s cỡ %s chỉ còn lại %s chiếc.",
					productDetail.getProduct().getName(), productDetail.getSize(), productDetail.getQuantity()));
		}

		OrderTimeline orderTimeline = new OrderTimeline();
		orderTimeline.setCreatedDate(new Date());
		orderTimeline.setStaff(new Account(authentication.getId()));
		orderTimeline.setOrder(order);
		orderTimeline.setOrderTimelineType(OrderTimelineType.EDITED);
		orderTimeline.setDescription(String.format("Thêm sản phẩm [%s] cỡ [%s], số lượng : %s",
				productDetail.getProduct().getName(), productDetail.getSize(), orderItem.getQuantity()));

		orderItem.setId(0);
		orderItem.setOrder(order);
		orderItem.setOrderItemStatus(OrderItemStatus.APPROVED);
		orderItem.setPrice(productDetail.getProduct().getSellPrice());
		orderItem.setProductDetail(productDetail);

		orderItemRepository.save(orderItem);

		order.setTotal(order.getTotal() + orderItem.getPrice() * orderItem.getQuantity());

		Optional<Voucher> voucherOptional = voucherRepository.findVoucherByOrderId(order.getId());
		if (voucherOptional.isPresent()) {
			Voucher selectedVoucher = voucherOptional.get();
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
		}

		if (order.getSaleMethod() == SaleMethod.DELIVERY) {
			order.setFee(deliveryService.getDeliveryFee(order.getTotal() - order.getDiscount(), order.getDistrictId(),
					order.getWardCode()));
		}

		if (productDetailRepository.decreaseProductDetailQuantity(orderItem.getProductDetail().getId(),
				orderItem.getQuantity()) != 1
				|| orderRepository.updateOrderAfterChange(order.getId(), order.getTotal(), order.getFee(),
						order.getDiscount(), order.getVoucher()) != 1) {
			throw new ValidationException();
		}

		orderTimelineRepository.save(orderTimeline);
	}

	@Transactional
	@Override
	public void updateOrderItemQuantity(int id, int quantity, String description, AccountAuthentication authentication)
			throws JsonMappingException, JsonProcessingException {
		if (quantity <= 0) {
			throw new ValidationException("Số lượng không hợp lệ.");
		}
		Order order = orderRepository.findStagingOrder(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order Item not found with id : " + id));

		if (order.getOrderStatus() != OrderStatus.PENDING && order.getOrderStatus() != OrderStatus.CONFIRMED) {
			throw new ValidationException("Trạng thái đơn hàng không hợp lệ.");
		}

		OrderItem orderItem = orderItemRepository.findOrderItem(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order item not found with id : " + id));

		if (quantity == orderItem.getQuantity()) {
			return;
		}

		OrderTimeline orderTimeline = new OrderTimeline();
		orderTimeline.setCreatedDate(new Date());
		orderTimeline.setOrder(order);
		orderTimeline.setOrderTimelineType(OrderTimelineType.EDITED);
		orderTimeline.setStaff(new Account(authentication.getId()));

		if (quantity < orderItem.getQuantity()) {
			if (quantity > orderItem.getProductDetail().getQuantity()) {
				throw new ValidationException(String.format("Sản phẩm %s cỡ %s chỉ còn lại %s chiếc.",
						orderItem.getProductDetail().getProduct().getName(), orderItem.getProductDetail().getSize(),
						orderItem.getProductDetail().getQuantity()));
			}
			order.setTotal(order.getTotal() - orderItem.getPrice() * (orderItem.getQuantity() - quantity));
			orderTimeline.setDescription(String.format("Giảm số lượng sản phẩm [%s] Cỡ [%s] xuống còn %s chiếc. %s",
					orderItem.getProductDetail().getProduct().getName(), orderItem.getProductDetail().getSize(),
					quantity, description));
			Optional<Voucher> voucherOptional = voucherRepository.findVoucherByOrderId(order.getId());
			if (voucherOptional.isPresent()) {
				Voucher selectedVoucher = voucherOptional.get();
				if (order.getTotal() < selectedVoucher.getRequiredValue()) {
					order.setDiscount(0);
					order.setVoucher(null);
				} else {
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
				}
			}

			if (order.getSaleMethod() == SaleMethod.DELIVERY) {
				order.setFee(deliveryService.getDeliveryFee(order.getTotal() - order.getDiscount(),
						order.getDistrictId(), order.getWardCode()));
			}

			if (orderItemRepository.updateOrderItemQuantity(orderItem.getId(), quantity) != 1
					|| productDetailRepository.increaseProductDetailQuantity(orderItem.getProductDetail().getId(),
							orderItem.getQuantity() - quantity) != 1
					|| orderRepository.updateOrderAfterChange(order.getId(), order.getTotal(), order.getFee(),
							order.getDiscount(), order.getVoucher()) != 1) {
				throw new ValidationException();
			}
		} else {
			orderTimeline.setDescription(String.format("Thêm sản phẩm [%s] Cỡ [%s] thêm %s chiếc. %s",
					orderItem.getProductDetail().getProduct().getName(), orderItem.getProductDetail().getSize(),
					quantity - orderItem.getQuantity(), description));

			OrderItem newOrderItem = new OrderItem();
			newOrderItem.setOrder(order);
			newOrderItem.setOrderItemStatus(OrderItemStatus.APPROVED);
			newOrderItem.setPrice(orderItem.getProductDetail().getProduct().getSellPrice());
			newOrderItem.setProductDetail(orderItem.getProductDetail());
			newOrderItem.setQuantity(quantity - orderItem.getQuantity());

			orderItemRepository.save(newOrderItem);

			order.setTotal(order.getTotal() + newOrderItem.getQuantity() * newOrderItem.getPrice());

			Optional<Voucher> voucherOptional = voucherRepository.findVoucherByOrderId(order.getId());
			if (voucherOptional.isPresent()) {
				Voucher selectedVoucher = voucherOptional.get();
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
			}

			if (order.getSaleMethod() == SaleMethod.DELIVERY) {
				order.setFee(deliveryService.getDeliveryFee(order.getTotal() - order.getDiscount(),
						order.getDistrictId(), order.getWardCode()));
			}

			if (productDetailRepository.decreaseProductDetailQuantity(newOrderItem.getProductDetail().getId(),
					newOrderItem.getQuantity()) != 1
					|| orderRepository.updateOrderAfterChange(order.getId(), order.getTotal(), order.getFee(),
							order.getDiscount(), order.getVoucher()) != 1) {
				throw new ValidationException();
			}
		}

		orderTimelineRepository.save(orderTimeline);
	}

	@Transactional
	@Override
	public void deleteOrderItem(int id, String description, AccountAuthentication authentication)
			throws JsonMappingException, JsonProcessingException {
		Order order = orderRepository.findStagingOrder(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order Item not found with id : " + id));
		if (order.getOrderStatus() != OrderStatus.PENDING && order.getOrderStatus() != OrderStatus.CONFIRMED) {
			throw new ValidationException("Trạng thái đơn hàng không hợp lệ.");
		}

		OrderTimeline orderTimeline = new OrderTimeline();
		orderTimeline.setCreatedDate(new Date());
		orderTimeline.setStaff(new Account(authentication.getId()));
		orderTimeline.setOrder(order);
		if (orderRepository.getCountOrderItemByOrderId(order.getId(), OrderItemStatus.APPROVED) <= 1) {
			orderTimeline.setOrderTimelineType(OrderTimelineType.CANCELLED);
			orderTimeline.setDescription(String.format("Đã hủy đơn hàng. %s", description));
			if (orderRepository.updateOrderStatus(order.getId(), OrderStatus.CANCELLED) != 1) {
				throw new ResourceNotFoundException("Không tìm thấy đơn hàng.");
			}
		} else {
			OrderItem orderItem = orderItemRepository.findOrderItem(id)
					.orElseThrow(() -> new ResourceNotFoundException("Order item not found with id : " + id));
			order.setTotal(order.getTotal() - orderItem.getQuantity() * orderItem.getPrice());

			orderTimeline.setOrderTimelineType(OrderTimelineType.EDITED);
			orderTimeline.setDescription(String.format("Đã loại bỏ sản phẩm [%s] cỡ [%s] ra khỏi đơn hàng. %s",
					orderItem.getProductDetail().getProduct().getName(), orderItem.getProductDetail().getSize(),
					description));

			Optional<Voucher> voucherOptional = voucherRepository.findVoucherByOrderId(order.getId());
			if (voucherOptional.isPresent()) {
				Voucher selectedVoucher = voucherOptional.get();
				if (order.getTotal() < selectedVoucher.getRequiredValue()) {
					order.setDiscount(0);
					order.setVoucher(null);
				} else {
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
				}
			}

			if (order.getSaleMethod() == SaleMethod.DELIVERY) {
				order.setFee(deliveryService.getDeliveryFee(order.getTotal() - order.getDiscount(),
						order.getDistrictId(), order.getWardCode()));
			}

			orderItemRepository.deleteOrderItemById(id);

			if (productDetailRepository.increaseProductDetailQuantity(orderItem.getProductDetail().getId(),
					orderItem.getQuantity()) != 1
					|| orderRepository.updateOrderAfterChange(order.getId(), order.getTotal(), order.getFee(),
							order.getDiscount(), order.getVoucher()) != 1) {
				throw new ValidationException();
			}
		}
		orderTimelineRepository.save(orderTimeline);
	}

}
