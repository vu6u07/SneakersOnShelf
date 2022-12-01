package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

	@Query(value = "SELECT i.image FROM ProductImage i WHERE i.product.id = :id")
	List<String> findProductImageDTOByProductId(int id);

	@Query(value = "SELECT new com.sos.entity.ProductImage(p.id, p.image) FROM ProductImage p WHERE p.product.id = :id")
	List<ProductImage> findByProductId(int id);

	@Modifying
	@Query(value = "UPDATE Product p SET p.productImage = :productImage WHERE p.id = :productId AND p.productImage IS NULL")
	int setDefaultProductImageIfNull(int productId, ProductImage productImage);
	
	@Modifying
	@Query(value = "UPDATE Product p SET p.productImage = :productImage WHERE p.id = :productId")
	int setDefaultProductImage(int productId, ProductImage productImage);

	@Query(value = "SELECT p.product.id FROM ProductImage p WHERE p.id = :id")
	Optional<Integer> getProductIdByProductImageId(int id);
	
}
