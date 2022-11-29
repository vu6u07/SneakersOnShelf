package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

	@Query(value = "SELECT new com.sos.entity.ProductDetail(p.id, p.size, p.quantity) FROM ProductDetail p WHERE p.product.id = :id")
	List<ProductDetail> findByProductId(int id);
	
	@Query(value = "SELECT new com.sos.entity.ProductDetail(p.id, p.size, p.quantity) FROM ProductDetail p WHERE p.product.id = :id AND p.activeStatus = :activeStatus")
	List<ProductDetail> findByProductId(int id, ActiveStatus activeStatus);

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
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.quantity = :quantity WHERE p.id = :id")
	int updateProductDetailQuantity(int id, int quantity);
	
	//admin
	@Query(value = "SELECT new com.sos.entity.ProductDetail(pd.id, pd.quantity) FROM ProductDetail pd WHERE pd.product.id = :productId AND pd.size = :size")
	Optional<ProductDetail> findProductDetail(int productId, String size);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.activeStatus = :activeStatus WHERE p.id = :id")
	int updateProductDetailStatus(int id, ActiveStatus activeStatus);
	
}
