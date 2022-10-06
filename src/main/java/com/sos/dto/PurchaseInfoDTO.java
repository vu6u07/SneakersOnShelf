package com.sos.dto;

import java.util.Date;
import java.util.List;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.PaymentStatus;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInfoDTO {

	private int id;

	private String userTokenQuery;

	private List<CartItemDTO> items;

	private Delivery delivery;
	
	private CustomerInfo customerInfo;

	private long discount;

	private long surcharge;

	private long total;

	private String description;

	private OrderStatus orderStatus;

	private PaymentStatus paymentStatus;

	private PaymentMethod paymentMethod;

	private Date createDate;

	private Date updateDate;

	public PurchaseInfoDTO(int id, String userTokenQuery, long discount, long surcharge, long total, String description,
			OrderStatus orderStatus, PaymentStatus paymentStatus, PaymentMethod paymentMethod, Date createDate,
			Date updateDate) {
		this.id = id;
		this.userTokenQuery = userTokenQuery;
		this.discount = discount;
		this.surcharge = surcharge;
		this.total = total;
		this.description = description;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
