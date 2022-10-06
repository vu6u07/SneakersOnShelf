package com.sos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	private Account staff;

	@ManyToOne
	private CustomerInfo customerInfo;

	private String userTokenQuery;

	@JsonManagedReference
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;

	@OneToOne(cascade = CascadeType.PERSIST)
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

	public Order(int id) {
		this.id = id;
	}

	public Order(int id, String userTokenQuery, OrderStatus orderStatus, Date createDate) {
		this.id = id;
		this.userTokenQuery = userTokenQuery;
		this.orderStatus = orderStatus;
		this.createDate = createDate;
	}

}
