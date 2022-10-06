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

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.quantity = p.quantity - :amount WHERE p.id = :id")
	void decreaseProductDetailQuantity(int id, int amount);
	
}
