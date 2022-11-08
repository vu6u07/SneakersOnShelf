package com.sos.repository;

import java.util.List;

import com.sos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sos.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

	@Query(value = "SELECT i.image FROM ProductImage i WHERE i.product.id = :id")
	List<String> findProductImageDTOByProductId(int id);
	@Modifying
	@Query(value = "DELETE FROM ProductImage b WHERE b.product = :product")
	void deleteProductImageByProduct(@Param("product") Product product);

	@Query(value = "UPDATE ProductImage b SET b.product = null WHERE b.product = :product")
	void updateByProduct(@Param("product") Product product);
}
