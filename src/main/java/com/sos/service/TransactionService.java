package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.TransactionType;
import com.sos.dto.TransactionDTO;
import com.sos.entity.Transaction;
import com.sos.security.AccountAuthentication;

public interface TransactionService {

	Transaction save(Transaction transaction, AccountAuthentication authentication);
	
	Page<TransactionDTO> findTransactions(String query, TransactionType transactionType, PaymentMethod paymentMethod, Pageable pageable);

}
