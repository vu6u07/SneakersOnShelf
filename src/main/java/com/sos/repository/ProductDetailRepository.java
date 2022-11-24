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
	
	//with product
	@Query(value = "SELECT new com.sos.entity.ProductDetail(pd.id, pd.size, pd.quantity, p.id, p.name, p.sellPrice) FROM ProductDetail pd JOIN pd.product p WHERE pd.id = :id")
	Optional<ProductDetail> findProductDetailById(int id);

	@Modifying
	@Query(value = "DELETE FROM ProductDetail b WHERE b.product = :product")
	void deleteProductDetailByProduct(@Param("product") Product product);

	@Query(value = "SELECT new com.sos.entity.ProductDetail(c.id,c.size, c.quantity) FROM ProductDetail c WHERE c.id = :id")
	ProductDetail findById(@Param("id") int id);

	@Modifying
	@Query(value = "UPDATE ProductDetail b SET b.quantity = :quantity where b.id = :id")
	int updateQuantityById(@Param("quantity") int quantity, @Param("id") int id);

	@Modifying
	@Query(value = "DELETE FROM ProductDetail b WHERE b.id = :id")
	void deleteProductDetailById(@Param("id") int id);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.quantity = p.quantity - :amount WHERE p.id = :id AND p.quantity >= :amount")
	int decreaseProductDetailQuantity(int id, int amount);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ProductDetail p SET p.quantity = p.quantity + :amount WHERE p.id = :id")
	int increaseProductDetailQuantity(int id, int amount);
	
}
