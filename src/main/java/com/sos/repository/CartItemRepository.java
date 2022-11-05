package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.CartStatus;
import com.sos.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	@Query(value = "SELECT new com.sos.entity.CartItem(c.id, c.productDetail, c.quantity) FROM CartItem c WHERE c.cart.id = :cartId")
	List<CartItem> findCartItemsByCartId(int cartId);
	
	@Modifying
	@Query(value = "UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :id")
	int changeCartItemQuantity(int id, int quantity);
	
	@Query(value = "SELECT c FROM CartItem c WHERE c.cart.id = :cartId AND c.productDetail.id = :productDetailId")
	Optional<CartItem> findByCartIdAndProductDetailId(int cartId, int productDetailId);
	
	@Modifying
	@Query(value = "DELETE FROM CartItem c WHERE c.id = :id")
	int deleteCartItemById(int id);
	
	@Modifying
	@Query(value = "DELETE FROM CartItem c WHERE c.cart.id = :cartId")
	int deleteCartItemByCartId(int cartId);
	
	// Anonymous
	@Query(value = "SELECT new com.sos.entity.CartItem(c.id) FROM CartItem c WHERE c.id =:id AND c.cart.cartStatus = :cartStatus AND c.cart.token = :token")
	Optional<CartItem> findCartItemId(int id, CartStatus cartStatus, String token);

	// Logged User
	@Query(value = "SELECT new com.sos.entity.CartItem(c.id) FROM CartItem c WHERE c.id =:id AND c.cart.cartStatus = :cartStatus AND c.cart.account.id = :accountId")
	Optional<CartItem> findCartItemId(int id, CartStatus cartStatus, int accountId);

}
