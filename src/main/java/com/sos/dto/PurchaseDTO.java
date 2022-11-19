package com.sos.dto;

import java.util.Date;

import com.sos.common.ApplicationConstant.OrderStatus;
import com.sos.common.ApplicationConstant.SaleMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

	private String id;

	private String fullname;

	private String phone;

	private long productCount;

	private long total;

	private SaleMethod saleMethod;
	
	private OrderStatus orderStatus;

	private Date createDate;

}
