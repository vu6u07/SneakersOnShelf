package com.sos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

	@Query(value = "SELECT i.image FROM ProductImage i WHERE i.product.id = :id")
	List<String> findProductImageDTOByProductId(int id);

}
