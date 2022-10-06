package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

	@Query(value = "SELECT new com.sos.entity.OrderItem(o.id) FROM OrderItem o WHERE o.id = :id AND o.order.id = :orderId AND o.order.userTokenQuery = :userTokenQuery AND o.order.orderStatus = :orderStatus")
	Optional<OrderItem> findOrderItem(int id, int orderId, String userTokenQuery, OrderStatus orderStatus);

	@Query(value = "SELECT new com.sos.entity.OrderItem(o.id, o.quantity) FROM OrderItem o WHERE o.order.id = :orderId AND o.productDetail.id = :productId")
	Optional<OrderItem> findByOrderIdAndProductId(int orderId, int productId);

	@Query(value = "SELECT new com.sos.entity.OrderItem(o.id, o.productDetail.id, o.productDetail.quantity, o.productDetail.size, o.productDetail.product.name, o.productDetail.product.sellPrice, o.quantity) FROM OrderItem o WHERE o.order.id = :orderId")
	List<OrderItem> findOrderItemsByOrderId(int orderId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE OrderItem o SET o.price = :price WHERE o.id = :id")
	void updateOrderItemPrice(int id, long price);

}
