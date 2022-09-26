package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Account employee;

	@ManyToOne
	private CustomerInfo customerInfo;

	@OneToOne
	private Delivery delivery;

	@ManyToOne
	private Voucher voucher;

	private long discount;

	private long surcharge;

	private long total;

	private String description;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	private Date createDate;

	private Date updateDate;

}
