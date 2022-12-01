package com.sos.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.dto.CollectionProductDTO;
import com.sos.dto.ProductInfoDTO;
import com.sos.entity.Brand;
import com.sos.entity.Category;
import com.sos.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = "SELECT new com.sos.dto.ProductInfoDTO(p.id, p.name, p.productStatus, p.productGender, p.brand.name, p.category.name, p.productImage.image, p.sellPrice, p.originalPrice, p.description) FROM Product p WHERE p.id = :id")
	Optional<ProductInfoDTO> findProductInfoDTOById(int id);
	
	// Collection
	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice, p.productGender, p.productStatus, p.createDate, p.updateDate) FROM Product p LEFT JOIN p.productImage i WHERE (:query IS NULL OR p.name LIKE :query) AND (:brandId IS NULL OR p.brand.id = :brandId) AND (:categoryId IS NULL OR p.category.id = :categoryId) AND (:productGender IS NULL OR p.productGender = :productGender) AND (:productStatus IS NULL OR p.productStatus = :productStatus)")
	Page<CollectionProductDTO> findCollectionProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, ProductStatus productStatus, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.CollectionProductDTO(p.id, p.name, i.image, p.sellPrice, p.originalPrice, p.productGender, p.productStatus, p.createDate, p.updateDate) FROM OrderItem od JOIN od.order o JOIN od.productDetail pd JOIN pd.product p LEFT JOIN p.productImage i WHERE o.orderStatus = 'APPROVED' AND od.orderItemStatus = 'APPROVED' AND (:query IS NULL OR p.name LIKE :query) AND (:brandId IS NULL OR p.brand.id = :brandId) AND (:categoryId IS NULL OR p.category.id = :categoryId) AND (:productGender IS NULL OR p.productGender = :productGender) AND (:productStatus IS NULL OR p.productStatus = :productStatus) GROUP BY p.id, p.name, i.image, p.sellPrice, p.originalPrice, p.productGender, p.productStatus, p.createDate, p.updateDate ORDER BY SUM(od.quantity) DESC")
	Page<CollectionProductDTO> findBestSellingProductDTO(String query, Integer brandId, Integer categoryId,
			ProductGender productGender, ProductStatus productStatus, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Product p SET p.name = :name, p.productGender = :productGender, p.productStatus = :productStatus, p.brand = :brand, p.category = :category, p.sellPrice = :sellPrice, p.updateDate = :date, p.description = :description WHERE p.id = :id")
	int updateProduct(int id, String name, ProductGender productGender, ProductStatus productStatus, Brand brand, Category category, long sellPrice, String description, Date date);
	
}
