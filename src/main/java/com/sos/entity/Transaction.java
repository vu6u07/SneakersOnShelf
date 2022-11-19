package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.TransactionStatus;
import com.sos.common.ApplicationConstant.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Order order;

	@ManyToOne
	private Account staff;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	private long amount;

	private Date createDate;

	private Date updateDate;

}
