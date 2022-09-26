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
import com.sos.common.ApplicationConstant.ReturnType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Refund {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private OrderItem orderItem;

	@ManyToOne
	private Account employee;

	private int quantity;

	private long amount;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	private ReturnType returnType;

	private String description;

	private Date createDate;

}
