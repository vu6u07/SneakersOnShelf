package com.sos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i")
	Page<CollectionProductDTO> findCollectionProductDTO(Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.ProductInfoDTO(p.id, p.name, p.productGender, p.brand.name, p.category.name, p.productImage.image, p.sellPrice, p.originalPrice, p.description) FROM Product p WHERE p.id = :id")
	Optional<ProductInfoDTO> findProductInfoDTOById(int id);

}
