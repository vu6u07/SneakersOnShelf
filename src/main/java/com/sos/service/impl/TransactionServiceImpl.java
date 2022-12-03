package com.sos.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.TransactionStatus;
import com.sos.common.ApplicationConstant.TransactionType;
import com.sos.dto.TransactionDTO;
import com.sos.entity.Account;
import com.sos.entity.Transaction;
import com.sos.repository.TransactionRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Transaction save(Transaction transaction, AccountAuthentication authentication) {
		Date now = new Date();
		transaction.setId(0);
		transaction.setCreateDate(now);
		transaction.setUpdateDate(now);
		transaction.setTransactionStatus(TransactionStatus.APPROVED);
		transaction.setStaff(new Account(authentication.getId()));
		return transactionRepository.save(transaction);
	}

	@Override
	public Page<TransactionDTO> findTransactions(String query, TransactionStatus transactionStatus,
			TransactionType transactionType, PaymentMethod paymentMethod, Pageable pageable) {
		return transactionRepository.findTransactions(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null,
				transactionStatus, transactionType, paymentMethod, pageable);
	}
}
