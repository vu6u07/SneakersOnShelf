package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.OrderItemStatus;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.dto.CartItemDTO;
import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.entity.Order;
import com.sos.entity.Voucher;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

	@Query(value = "SELECT new com.sos.dto.CartItemDTO(o.id, o.quantity, o.productDetail.product.id, o.productDetail.id, o.productDetail.product.name, o.productDetail.size, o.productDetail.product.productImage.image, o.price, r, o.orderItemStatus) FROM OrderItem o LEFT JOIN o.rate r WHERE o.order.id = :id")
	List<CartItemDTO> findAllPurchaseItemDTO(String id);

	// Anonymous
	@Query(value = "SELECT new com.sos.dto.PurchaseInfoDTO(o.id, o.token, o.saleMethod, o.orderStatus, o.discount, o.surcharge, o.total, o.fee, o.fullname, o.email, o.phone, o.description, o.createDate, o.address, o.detailedAddress) FROM Order o WHERE o.id = :id AND o.token = :token")
	Optional<PurchaseInfoDTO> findPurchaseInfoDTO(String id, String token);

	// Logged User
	@Query(value = "SELECT new com.sos.dto.PurchaseInfoDTO(o.id, o.token, o.saleMethod, o.orderStatus, o.discount, o.surcharge, o.total, o.fee, o.fullname, o.email, o.phone, o.description, o.createDate, o.address, o.detailedAddress) FROM Order o WHERE o.id = :id AND o.account.id = :accountId")
	Optional<PurchaseInfoDTO> findPurchaseInfoDTO(String id, int accountId);

	@Query(value = "SELECT new com.sos.dto.PurchaseDTO(o.id, o.fullname, o.phone, COUNT(oi.quantity), (o.total - o.discount + o.fee), o.saleMethod, o.orderStatus, o.createDate) FROM Order o JOIN o.orderItems oi WHERE o.account.id = :accountId GROUP BY o.id, o.fullname, o.phone, o.total, o.discount, o.fee, o.saleMethod, o.orderStatus, o.createDate")
	Page<PurchaseDTO> findAllPurchaseDTOByAccountId(int accountId, Pageable pageable);

	// Admin
	@Query(value = "SELECT new com.sos.dto.PurchaseInfoDTO(o.id, o.token, o.saleMethod, o.orderStatus, o.discount, o.surcharge, o.total, o.fee, o.fullname, o.email, o.phone, o.description, o.createDate, o.address, o.detailedAddress) FROM Order o WHERE o.id = :id")
	Optional<PurchaseInfoDTO> findPurchaseInfoDTO(String id);

	@Query(value = "SELECT new com.sos.dto.PurchaseDTO(o.id, o.fullname, o.phone, COUNT(oi.quantity), (o.total - o.discount + o.fee), o.saleMethod, o.orderStatus, o.createDate) FROM Order o JOIN o.orderItems oi WHERE o.id = :id GROUP BY o.id, o.fullname, o.phone, o.total, o.discount, o.fee, o.saleMethod, o.orderStatus, o.createDate")
	Optional<PurchaseDTO> findPurchaseDTOById(String id);

	@Query(value = "SELECT new com.sos.dto.PurchaseDTO(o.id, o.fullname, o.phone, COUNT(oi.quantity), (o.total - o.discount + o.fee), o.saleMethod, o.orderStatus, o.createDate) FROM Order o JOIN o.orderItems oi GROUP BY o.id, o.fullname, o.phone, o.total, o.discount, o.fee, o.saleMethod, o.orderStatus, o.createDate")
	Page<PurchaseDTO> findAllPurchaseDTOs(Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.PurchaseDTO(o.id, o.fullname, o.phone, COUNT(oi.quantity), (o.total - o.discount + o.fee), o.saleMethod, o.orderStatus, o.createDate) FROM Order o JOIN o.orderItems oi WHERE o.orderStatus = :orderStatus GROUP BY o.id, o.fullname, o.phone, o.total, o.discount, o.fee, o.saleMethod, o.orderStatus, o.createDate")
	Page<PurchaseDTO> findAllPurchaseDTOs(OrderStatus orderStatus, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.PurchaseDTO(o.id, o.fullname, o.phone, COUNT(oi.quantity), (o.total - o.discount + o.fee), o.saleMethod, o.orderStatus, o.createDate) FROM Order o JOIN o.orderItems oi WHERE CAST(o.id AS string) LIKE :query OR o.fullname LIKE :query GROUP BY o.id, o.fullname, o.phone, o.total, o.discount, o.fee, o.saleMethod, o.orderStatus, o.createDate")
	Page<PurchaseDTO> findAllPurchaseDTOs(String query, Pageable pageable);
	
	@Query(value = "SELECT new com.sos.dto.PurchaseDTO(o.id, o.fullname, o.phone, COUNT(oi.quantity), (o.total - o.discount + o.fee), o.saleMethod, o.orderStatus, o.createDate) FROM Order o JOIN o.orderItems oi WHERE o.orderStatus = :orderStatus AND (CAST(o.id AS string) LIKE :query OR o.fullname LIKE :query) GROUP BY o.id, o.fullname, o.phone, o.total, o.discount, o.fee, o.saleMethod, o.orderStatus, o.createDate")
	Page<PurchaseDTO> findAllPurchaseDTOs(String query, OrderStatus orderStatus, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.id = :id")
	int updateOrderStatus(String id, OrderStatus orderStatus);
	
	//for update order address
	@Query(value = "SELECT new com.sos.entity.Order(o.id, o.orderStatus, o.saleMethod, o.discount, o.surcharge, o.total, o.districtId, o.wardCode) FROM Order o WHERE o.id = :id")
	Optional<Order> findStagingOrder(String id);
	
	//for update order address
	@Query(value = "SELECT new com.sos.entity.Order(o.id, o.orderStatus, o.saleMethod, o.discount, o.surcharge, o.total, o.districtId, o.wardCode) FROM OrderItem oi JOIN oi.order o WHERE oi.id = :orderItemId")
	Optional<Order> findStagingOrder(int orderItemId);

	@Modifying
	@Query(value = "UPDATE Order o SET o.fee = :fee, o.fullname = :fullname, o.phone = :phone, o.provinceId = :provinceId, o.districtId = :districtId, o.wardCode = :wardCode, o.address = :address, o.detailedAddress = :detailedAddress WHERE o.id = :id")
	int updateOrderAddress(String id, long fee, String fullname, String phone, int provinceId, int districtId, String wardCode, String address, String detailedAddress);
	
	//for update order item quantity (decrease)
	@Modifying
	@Query(value = "UPDATE Order o SET o.total = :total, o.fee = :fee, o.discount = :discount, o.voucher = :voucher WHERE o.id = :id")
	int updateOrderAfterChange(String id, long total, long fee, long discount, Voucher voucher);
	
	//for delete order item
	@Query(value = "SELECT COUNT(o.id) FROM OrderItem o WHERE o.order.id = :id AND o.orderItemStatus = :orderItemStatus")
	long getCountOrderItemByOrderId(String id, OrderItemStatus orderItemStatus);
}
