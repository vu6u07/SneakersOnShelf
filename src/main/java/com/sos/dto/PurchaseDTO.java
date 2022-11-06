package com.sos.dto;

import java.util.Date;
import java.util.UUID;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

	private UUID id;
	
	private long productCount;

	private long total;

	private OrderStatus orderStatus;

	private PaymentStatus paymentStatus;

	private Date createDate;

	private Date updateDate;

}
