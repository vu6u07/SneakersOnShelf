package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.TransactionType;
import com.sos.common.SorterConstant.TransactionSorter;
import com.sos.entity.Transaction;
import com.sos.security.AccountAuthentication;
import com.sos.service.TransactionService;

@RestController
@RequestMapping(value = "/admin/v1")
public class TransactionAdminController {

	@Autowired
	private TransactionService transactionService;

	// @formatter:off
	@PostMapping(value = "/transactions")
	public ResponseEntity<?> post(
			@RequestBody Transaction transaction, 
			AccountAuthentication authentication,
			HttpServletRequest request) throws URISyntaxException {
		Transaction created = transactionService.save(transaction, authentication);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString())).build();
	}
	// @formatter:on

	// @formatter:off
	@GetMapping(value = "/transactions")
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "transaction-type", required = false) TransactionType transactionType,
			@RequestParam(name = "payment-method", required = false) PaymentMethod paymentMethod,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "date_desc") TransactionSorter sorter){
		return ResponseEntity.ok(transactionService.findTransactions(query, transactionType, paymentMethod, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on
}
