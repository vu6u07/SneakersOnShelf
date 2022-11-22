package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sos.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

	@Query(value = "SELECT new com.sos.entity.ProductDetail(p.id, p.size, p.quantity) FROM ProductDetail p WHERE p.product.id = :id")
	List<ProductDetail> findByProductId(int id);

	@Query(value = "SELECT new com.sos.entity.ProductDetail(p.id, p.quantity) FROM ProductDetail p WHERE p.id = :id")
	Optional<ProductDetail> findByProductDetailId(int id);
	
	@Query(value = "SELECT new com.sos.entity.ProductDetail(c.productDetail.id, c.productDetail.quantity) FROM CartItem c WHERE c.id = :cartItemId")
	Optional<ProductDetail> findByCartItemId(int cartItemId);
	
	//with product
	@Query(value = "SELECT new com.sos.entity.ProductDetail(pd.id, pd.size, pd.quantity, p.id, p.name, p.sellPrice) FROM ProductDetail pd JOIN pd.product p WHERE pd.id = :id")
	Optional<ProductDetail> findProductDetailById(int id);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.quantity = p.quantity - :amount WHERE p.id = :id AND p.quantity >= :amount")
	int decreaseProductDetailQuantity(int id, int amount);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.quantity = p.quantity + :amount WHERE p.id = :id")
	int increaseProductDetailQuantity(int id, int amount);
}
