package com.sos.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
	@Type(type = "org.hibernate.type.UUIDCharType")
	private UUID id;

	@ManyToOne
	private Account staff;

	@ManyToOne
	private CustomerInfo customerInfo;

	private String token;

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
	
	private String email;
	
	public Order(UUID id, String token, OrderStatus orderStatus, Date createDate) {
		this.id = id;
		this.token = token;
		this.orderStatus = orderStatus;
		this.createDate = createDate;
	}

}
