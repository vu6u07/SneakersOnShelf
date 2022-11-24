package com.sos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.SaleMethod;

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
	private String id;

	private String token;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	private SaleMethod saleMethod;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@JsonManagedReference
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;

	@ManyToOne(fetch = FetchType.LAZY)
	private Voucher voucher;

	private long discount;

	private long surcharge;

	private long total;

	private long fee;

	private String fullname;

	private String email;

	private String phone;

	private String description;

	private Date createDate;

	private int provinceId;

	private int districtId;

	private String wardCode;

	private String address;

	private String detailedAddress;

	public Order(String id) {
		this.id = id;
	}

	public Order(String id, OrderStatus orderStatus, SaleMethod saleMethod, long discount, long surcharge, long total,
			int districtId, String wardCode) {
		this.id = id;
		this.orderStatus = orderStatus;
		this.saleMethod = saleMethod;
		this.discount = discount;
		this.surcharge = surcharge;
		this.total = total;
		this.districtId = districtId;
		this.wardCode = wardCode;
	}

}
