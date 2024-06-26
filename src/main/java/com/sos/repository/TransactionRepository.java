package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.TransactionStatus;
import com.sos.common.ApplicationConstant.TransactionType;
import com.sos.dto.TransactionDTO;
import com.sos.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query(value = "SELECT new com.sos.dto.TransactionDTO(t.id, COALESCE(a.fullname, ''), t.transactionType, t.transactionStatus, t.paymentMethod, t.amount, t.createDate, t.updateDate) FROM Transaction t LEFT JOIN t.staff a WHERE t.order.id = :id")
	List<TransactionDTO> findAllTransactionDTOByOrderId(String id);

	@Query(value = "SELECT new com.sos.dto.TransactionDTO(t.id, COALESCE(a.fullname, ''), o.id, t.transactionType, t.transactionStatus, t.paymentMethod, t.amount, t.createDate, t.updateDate) FROM Transaction t LEFT JOIN t.staff a JOIN t.order o WHERE (:query IS NULL OR (o.id LIKE :query OR a.fullname LIKE :query)) AND (:transactionStatus IS NULL OR t.transactionStatus = :transactionStatus) AND (:transactionType IS NULL OR t.transactionType = :transactionType) AND (:paymentMethod IS NULL OR t.paymentMethod = :paymentMethod)")
	Page<TransactionDTO> findTransactions(String query, TransactionStatus transactionStatus, TransactionType transactionType, PaymentMethod paymentMethod,
			Pageable pageable);
	
	@Query(value = "SELECT new com.sos.dto.TransactionDTO(t.id, COALESCE(a.fullname, ''), o.id, t.transactionType, t.transactionStatus, t.paymentMethod, t.amount, t.createDate, t.updateDate) FROM Transaction t LEFT JOIN t.staff a JOIN t.order o WHERE t.id = :id AND t.transactionStatus = :transactionStatus")
	Optional<TransactionDTO> findTransactionDTOById(int id, TransactionStatus transactionStatus);
	
	@Modifying
	@Query(value = "UPDATE Transaction t SET t.transactionStatus = :transactionStatus WHERE t.id = :id")
	int updateTransactionStatus(int id, TransactionStatus transactionStatus);
	
}
