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

import com.sos.common.ApplicationConstant.ExchangeStatus;
import com.sos.common.ApplicationConstant.ExchangeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exchange {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private OrderItem orderItem;

	@ManyToOne
	private ProductDetail productDetail;

	@OneToOne
	private Delivery delivery;

	@ManyToOne
	private CustomerInfo customerInfo;

	private int quantity;

	private long surcharge;

	@Enumerated(EnumType.STRING)
	private ExchangeType exchangeType;

	@Enumerated(EnumType.STRING)
	private ExchangeStatus exchangeStatus;

	private String description;

	private Date createdDate;
	
	private Date updateDate;

}
