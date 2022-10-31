package com.sos.repository;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	// Collection
	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i")
	Page<CollectionProductDTO> findCollectionProductDTO(Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.ProductInfoDTO(p.id, p.name, p.productGender, p.brand.name, p.category.name, p.productImage.image, p.sellPrice, p.originalPrice, p.description) FROM Product p where p.id = :id")
	Optional<ProductInfoDTO> findProductInfoDTOById(@Param("id") int id);

	@Query(value = "SELECT new com.sos.dto.ProductInfoDTO(p.id, p.name, p.productGender, p.brand.name, p.category.name, p.productImage.image, p.sellPrice, p.originalPrice, p.description) FROM Product p where p.name = :name")
	Optional<ProductInfoDTO> findProductInfoDTOByName(@Param("name") String name);

	@Query(value = "SELECT b FROM Product b WHERE b.name = :name")
	Product findProductByName(@Param("name") String name);
	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i WHERE p.brand.id = :brandId")
	Page<CollectionProductDTO> findCollectionProductDTOByBrandId(int brandId, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i WHERE p.productGender = :productGender")
	Page<CollectionProductDTO> findCollectionProductDTO(ProductGender productGender, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i WHERE p.category.id = :categoryId")
	Page<CollectionProductDTO> findCollectionProductDTOByCategoryId(int categoryId, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i WHERE p.brand.id = :brandId AND p.productGender = :productGender")
	Page<CollectionProductDTO> findCollectionProductDTO(int brandId, ProductGender productGender, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i WHERE p.brand.id = :brandId AND p.category.id = :categoryId")
	Page<CollectionProductDTO> findCollectionProductDTO(int brandId, int categoryId, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i WHERE p.brand.id = :brandId AND p.category.id = :categoryId AND p.productGender = :productGender")
	Page<CollectionProductDTO> findCollectionProductDTO(int brandId, int categoryId, ProductGender productGender,
			Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice) FROM Product p LEFT JOIN p.productImage i WHERE p.category.id = :categoryId AND p.productGender = :productGender")
	Page<CollectionProductDTO> findCollectionProductDTOByCategoryIdAndProductGender(int categoryId,
			ProductGender productGender, Pageable pageable);

}
