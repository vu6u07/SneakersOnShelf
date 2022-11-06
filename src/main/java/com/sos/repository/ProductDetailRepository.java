package com.sos.repository;

import java.util.List;
import java.util.Optional;

import com.sos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sos.entity.ProductDetail;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

	@Query(value = "SELECT new com.sos.entity.ProductDetail(p.id, p.size, p.quantity) FROM ProductDetail p WHERE p.product.id = :id")
	List<ProductDetail> findByProductId(int id);

	@Query(value = "SELECT new com.sos.entity.ProductDetail(p.id, p.quantity) FROM ProductDetail p WHERE p.id = :id")
	Optional<ProductDetail> findByProductDetailId(int id);

	@Query(value = "SELECT p FROM ProductDetail p WHERE p.product = :product AND p.size = :size")
	ProductDetail findByProductIdAAndSize(@Param("product") Product product, @Param("size") String size);
	
	@Query(value = "SELECT new com.sos.entity.ProductDetail(c.productDetail.id, c.productDetail.quantity) FROM CartItem c WHERE c.id = :cartItemId")
	Optional<ProductDetail> findByCartItemId(int cartItemId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.quantity = p.quantity - :amount WHERE p.id = :id")
	void decreaseProductDetailQuantity(int id, int amount);

	@Modifying
	@Query(value = "DELETE FROM ProductDetail b WHERE b.product = :product")
	void deleteProductDetailByProduct(@Param("product") Product product);
}
