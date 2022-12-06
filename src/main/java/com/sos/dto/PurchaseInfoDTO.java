package com.sos.dto;

import java.util.Date;
import java.util.List;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.SaleMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInfoDTO {

	private String id;

	private String token;

	private SaleMethod saleMethod;

	private OrderStatus orderStatus;

	private long memberOffer;

	private long discount;

	private long surcharge;

	private long total;

	private long fee;

	private String fullname;

	private String email;

	private String phone;

	private String description;

	private Date createDate;

	private String address;

	private String detailedAddress;

	private String paymentQRCode;

	private List<CartItemDTO> items;

	private List<OrderTimelineDTO> timelines;

	private List<TransactionDTO> transactions;

	public PurchaseInfoDTO(String id, String token, SaleMethod saleMethod, OrderStatus orderStatus, long memberOffer,
			long discount, long surcharge, long total, long fee, String fullname, String email, String phone,
			String description, Date createDate, String address, String detailedAddress) {
		this.id = id;
		this.token = token;
		this.saleMethod = saleMethod;
		this.orderStatus = orderStatus;
		this.memberOffer = memberOffer;
		this.discount = discount;
		this.surcharge = surcharge;
		this.total = total;
		this.fee = fee;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.description = description;
		this.createDate = createDate;
		this.address = address;
		this.detailedAddress = detailedAddress;
	}

}
