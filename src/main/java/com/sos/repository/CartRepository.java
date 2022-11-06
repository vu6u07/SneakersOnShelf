package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.CartStatus;
import com.sos.dto.CartDTO;
import com.sos.dto.CartItemDTO;
import com.sos.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	@Query(value = "SELECT new com.sos.dto.CartItemDTO(c.id, c.quantity, c.productDetail.product.id, c.productDetail.id, c.productDetail.product.name, c.productDetail.size, c.productDetail.product.productImage.image, c.productDetail.product.sellPrice) FROM CartItem c WHERE c.cart.id = :id")
	List<CartItemDTO> findAllCartItemDTOByCartId(int id);


	@Query(value = "SELECT COALESCE(SUM(ci.productDetail.product.sellPrice * ci.quantity), '0') FROM Cart c LEFT JOIN c.cartItems ci WHERE c.id = :id")
	long getTotal(int id);

	// Anonymous Cart
	@Query(value = "SELECT new com.sos.dto.CartDTO(c.id, c.token) FROM Cart c WHERE c.id = :id AND c.token = :token AND c.cartStatus = :cartStatus AND c.account IS NULL")
	Optional<CartDTO> findCartDTO(int id, CartStatus cartStatus, String token);


	@Query(value = "SELECT new com.sos.entity.Cart(c.id) FROM Cart c WHERE c.id = :id AND c.token = :token AND c.cartStatus = :cartStatus AND c.account IS NULL")
	Optional<Cart> findCartId(int id, CartStatus cartStatus, String token);

	@Query(value = "SELECT new com.sos.entity.Cart(c.id, c.token) FROM Cart c WHERE c.id = :id AND c.token = :token AND c.cartStatus = :cartStatus AND c.account IS NULL")
	Optional<Cart> findCart(int id, CartStatus cartStatus, String token);

	@Modifying
	@Query(value = "UPDATE Cart c SET c.cartStatus = :cartStatus WHERE c.id = :id")
	int updateCartStatus(int id, CartStatus cartStatus);

	// Cart
	@Query(value = "SELECT new com.sos.dto.CartDTO(a.cart.id) FROM Account a WHERE a.id = :accountId AND a.cart.cartStatus = :cartStatus")
	Optional<CartDTO> findCurrentCartDTOByAccountId(int accountId, CartStatus cartStatus);

	@Query(value = "SELECT new com.sos.dto.CartDTO(c.id) FROM Cart c WHERE c.id = :id AND c.account.id = :accountId AND c.cartStatus = :cartStatus")
	Optional<CartDTO> findCartDTO(int id, CartStatus cartStatus, int accountId);

	@Query(value = "SELECT new com.sos.entity.Cart(c.id) FROM Cart c WHERE c.id = :id AND c.account.id = :accountId AND c.cartStatus = :cartStatus")
	Optional<Cart> findCartId(int id, CartStatus cartStatus, int accountId);
}
