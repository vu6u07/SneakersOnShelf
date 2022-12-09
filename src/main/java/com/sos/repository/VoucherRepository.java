package com.sos.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.VoucherAccess;
import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.common.ApplicationConstant.VoucherType;
import com.sos.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

	// Admin
	@Query(value = "SELECT new com.sos.entity.Voucher(v.amount) FROM Voucher v WHERE v.id = :voucherId")
	Optional<Voucher> getVoucherAmount(int voucherId);

	@Modifying
	@Query(value = "UPDATE Voucher v SET v.voucherStatus = :voucherStatus WHERE v.id = :id")
	int updateVoucherStatus(int id, VoucherStatus voucherStatus);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v WHERE v.id = :id")
	Optional<Voucher> findVoucher(int id);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Order o JOIN o.voucher v WHERE o.id = :id")
	Optional<Voucher> findVoucherByOrderId(String id);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v")
	Page<Voucher> findAllVoucher(Pageable pageable);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v WHERE (:query IS NULL OR v.code LIKE :query) AND (:voucherType IS NULL OR v.voucherType = :voucherType) AND (:voucherAccess IS NULL OR v.voucherAccess = :voucherAccess) AND (:voucherStatus IS NULL OR v.voucherStatus = :voucherStatus)")
	Page<Voucher> findAllVoucher(String query, VoucherType voucherType, VoucherAccess voucherAccess,
			VoucherStatus voucherStatus, Pageable pageable);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v WHERE v.voucherStatus = :voucherStatus AND v.startDate <= :date AND v.experationDate > :date AND (v.quantity > 0 OR v.quantity = -1) AND v.id = :id")
	Optional<Voucher> findAvailableVoucherById(int id, VoucherStatus voucherStatus, Date date);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v WHERE v.voucherStatus = :voucherStatus AND v.startDate <= :date AND v.experationDate > :date AND (v.quantity > 0 OR v.quantity = -1)")
	Page<Voucher> findAllAvailableVoucher(Date date, VoucherStatus voucherStatus, Pageable pageable);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v WHERE v.voucherStatus = :voucherStatus AND v.startDate <= :date AND v.experationDate > :date AND (v.quantity > 0 OR v.quantity = -1) AND v.code LIKE :query")
	Page<Voucher> findAllAvailableVoucher(String query, Date date, VoucherStatus voucherStatus, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Voucher v SET v.quantity = v.quantity - 1 WHERE v.id = :id AND v.quantity > 0")
	int decreateVoucherQuantity(int id);

	// Set voucher expired
	@Modifying
	@Query(value = "UPDATE Voucher v SET v.voucherStatus = :voucherStatus WHERE v.experationDate < :date")
	int checkExpiredVoucher(VoucherStatus voucherStatus, Date date);

	// User
	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v WHERE v.voucherStatus = :voucherStatus AND v.voucherAccess = :voucherAccess AND v.startDate <= :date AND v.experationDate > :date AND (v.quantity > 0 OR v.quantity = -1)")
	Page<Voucher> findAllAvailableVoucher(Date date, VoucherStatus voucherStatus, VoucherAccess voucherAccess,
			Pageable pageable);

	@Query(value = "SELECT new com.sos.entity.Voucher(v.id, v.code, v.amount, v.requiredValue, v.maxValue, v.quantity, v.voucherStatus, v.voucherType, v.voucherAccess, v.createDate, v.startDate, v.experationDate) FROM Voucher v WHERE v.code = :code AND v.voucherStatus = :voucherStatus AND v.startDate <= :date AND v.experationDate > :date AND (v.quantity > 0 OR v.quantity = -1)")
	Page<Voucher> findAvailableVoucherByCode(String code, VoucherStatus voucherStatus, Date date, Pageable pageable);
}
