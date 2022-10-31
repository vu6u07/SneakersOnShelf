package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.dto.CartDTO;
import com.sos.dto.CartItemDTO;
import com.sos.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Order, Integer> {

	@Query(value = "SELECT new com.sos.entity.Order(o.id) FROM Order o WHERE o.id = :id AND o.userTokenQuery = :userTokenQuery")
	Optional<Order> findByOrderIdAndUserTokenQuery(int id, String userTokenQuery);

	@Query(value = "SELECT new com.sos.dto.CartDTO(o.id, o.userTokenQuery) FROM Order o WHERE o.id = :id AND o.userTokenQuery = :userTokenQuery")
	Optional<CartDTO> findCartDTOByOrderIdAndUserTokenQuery(int id, String userTokenQuery);

	@Query(value = "SELECT new com.sos.dto.CartItemDTO(o.order.id, o.quantity, o.productDetail.product.id, o.productDetail.product.name, o.productDetail.size, o.productDetail.product.productImage.image, o.productDetail.product.sellPrice) FROM OrderItem o WHERE o.order.id = :id")
	List<CartItemDTO> findAllCartItemDTOByOrderId(int id);

}
