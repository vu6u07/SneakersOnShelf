package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.dto.CartDTO;
import com.sos.dto.CartItemDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	// Cart
	@Query(value = "SELECT new com.sos.entity.Order(o.id) FROM Order o WHERE o.id = :id AND o.userTokenQuery = :userTokenQuery AND o.orderStatus = :orderStatus")
	Optional<Order> findOrderId(int id, String userTokenQuery, OrderStatus orderStatus);

	@Query(value = "SELECT new com.sos.dto.CartDTO(o.id, o.userTokenQuery) FROM Order o WHERE o.id = :id AND o.userTokenQuery = :userTokenQuery AND o.orderStatus = :orderStatus")
	Optional<CartDTO> findCartDTO(int id, String userTokenQuery, OrderStatus orderStatus);

	@Query(value = "SELECT new com.sos.dto.CartItemDTO(o.id, o.quantity, o.productDetail.product.id, o.productDetail.id, o.productDetail.product.name, o.productDetail.size, o.productDetail.product.productImage.image, o.productDetail.product.sellPrice) FROM OrderItem o WHERE o.order.id = :id")
	List<CartItemDTO> findAllCartItemDTO(int id);

	@Query(value = "SELECT new com.sos.entity.Order(o.id, o.userTokenQuery, o.orderStatus, o.createDate) FROM Order o WHERE o.id = :id AND o.userTokenQuery = :userTokenQuery AND o.orderStatus = :orderStatus")
	Optional<Order> findOrder(int id, String userTokenQuery, OrderStatus orderStatus);

	@Query(value = "SELECT COALESCE(SUM(oi.productDetail.product.sellPrice * oi.quantity), '0') FROM Order o LEFT JOIN o.orderItems oi WHERE o.id = :id")
	long getTotal(int id);
	
	// Purchase
	@Query(value = "SELECT new com.sos.dto.PurchaseInfoDTO(o.id, o.userTokenQuery, o.discount, o.surcharge, o.total, o.description, o.orderStatus, o.paymentStatus, o.paymentMethod, o.createDate, o.updateDate) FROM Order o WHERE o.id = :id AND o.userTokenQuery = :userTokenQuery AND o.orderStatus != :orderStatus")
	Optional<PurchaseInfoDTO> findPurchaseInfoDTO(int id, String userTokenQuery, OrderStatus orderStatus);

	@Query(value = "SELECT new com.sos.dto.CartItemDTO(o.id, o.quantity, o.productDetail.product.id, o.productDetail.id, o.productDetail.product.name, o.productDetail.size, o.productDetail.product.productImage.image, o.price, r) FROM OrderItem o LEFT JOIN o.rate r WHERE o.order.id = :id")
	List<CartItemDTO> findAllPurchaseItemDTO(int id);
	
}
