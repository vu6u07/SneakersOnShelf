package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

	@Query(value = "SELECT new com.sos.entity.Voucher(o.voucher.amount) FROM Order o WHERE o.id = :orderId")
	Optional<Voucher> getVoucherAmountByOrderId(int orderId);

}
