package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.OrderItemStatus;
import com.sos.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

	@Query(value = "SELECT new com.sos.entity.OrderItem(o.id) FROM OrderItem o WHERE o.id = :id AND o.order.account.id = :accountId")
	Optional<OrderItem> findOrderItem(int id, int accountId);
	
	@Query(value = "SELECT new com.sos.entity.OrderItem(o.id, o.price, o.quantity, pd.id, pd.quantity, pd.size, p.name, p.sellPrice, o.orderItemStatus) FROM OrderItem o JOIN o.productDetail pd JOIN pd.product p WHERE o.id = :id")
	Optional<OrderItem> findOrderItem(int id);

	@Modifying
	@Query(value = "UPDATE OrderItem oi SET oi.quantity = :quantity WHERE oi.id = :id")
	int updateOrderItemQuantity(int id, int quantity);
	
	@Modifying
	@Query(value = "UPDATE OrderItem oi SET oi.orderItemStatus = :orderItemStatus WHERE oi.id = :id")
	int updateOrderItemStatus(int id, OrderItemStatus orderItemStatus);
	
	@Modifying
	@Query(value = "DELETE FROM OrderItem o WHERE o.id = :id")
	int deleteOrderItemById(int id);
	
}
