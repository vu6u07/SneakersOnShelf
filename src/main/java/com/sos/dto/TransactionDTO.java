package com.sos.dto;

import java.util.Date;

import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.TransactionStatus;
import com.sos.common.ApplicationConstant.TransactionType;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

	private int id;

	private String staff;
	
	private String orderId;

	private TransactionType transactionType;

	private TransactionStatus transactionStatus;

	private PaymentMethod paymentMethod;

	private long amount;

	private Date createDate;

	private Date updateDate;

	public TransactionDTO(int id, String staff, TransactionType transactionType, TransactionStatus transactionStatus,
			PaymentMethod paymentMethod, long amount, Date createDate, Date updateDate) {
		this.id = id;
		this.staff = staff;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
