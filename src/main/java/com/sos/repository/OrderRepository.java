package com.sos.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.dto.CartItemDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

	@Query(value = "SELECT new com.sos.dto.CartItemDTO(o.id, o.quantity, o.productDetail.product.id, o.productDetail.id, o.productDetail.product.name, o.productDetail.size, o.productDetail.product.productImage.image, o.price, r) FROM OrderItem o LEFT JOIN o.rate r WHERE o.order.id = :id")
	List<CartItemDTO> findAllPurchaseItemDTO(UUID id);
	
	//Anonymous
	@Query(value = "SELECT new com.sos.dto.PurchaseInfoDTO(o.id, o.token, o.discount, o.surcharge, o.total, o.description, o.orderStatus, o.paymentStatus, o.paymentMethod, o.createDate, o.updateDate) FROM Order o WHERE o.id = :id AND o.token = :token")
	Optional<PurchaseInfoDTO> findPurchaseInfoDTO(UUID id, String token);
	
	//Logged User
	@Query(value = "SELECT new com.sos.dto.PurchaseInfoDTO(o.id, o.token, o.discount, o.surcharge, o.total, o.description, o.orderStatus, o.paymentStatus, o.paymentMethod, o.createDate, o.updateDate) FROM Order o WHERE o.id = :id AND o.customerInfo.account.id = :accountId")
	Optional<PurchaseInfoDTO> findPurchaseInfoDTO(UUID id, int accountId);
	
}
