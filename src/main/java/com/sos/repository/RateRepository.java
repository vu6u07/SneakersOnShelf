package com.sos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.dto.RateDTO;
import com.sos.entity.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

	@Query(value = "SELECT new com.sos.dto.RateDTO(r.id, r.score, r.comment, r.createDate, pd.size, ci.fullname, COALESCE(a.picture, '')) FROM OrderItem o JOIN o.rate r JOIN o.productDetail pd JOIN pd.product p JOIN o.order od JOIN od.customerInfo ci LEFT JOIN ci.account a WHERE p.id = :id")
	Page<RateDTO> findByProductDetailId(int id, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE OrderItem o SET o.rate = :rate WHERE o.id = :id")
	int updadteRateIdOfOrderItem(int id, Rate rate);

}
