package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

	@Query(value = "SELECT new com.sos.entity.OrderItem(o.id, o.quantity) FROM OrderItem o WHERE o.order.id = :id AND o.productDetail.id = :productId")
	Optional<OrderItem> findByOrderIdAndProductId(int id, int productId);

}
