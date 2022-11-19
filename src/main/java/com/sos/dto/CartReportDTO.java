package com.sos.dto;

import java.util.Date;

import com.sos.common.ApplicationConstant.CartStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartReportDTO {

	private int id;

	private long productCount;

	private long total;

	private CartStatus cartStatus;

	private Date createDate;

	private Date updateDate;

	public CartReportDTO(int id, long productCount, CartStatus cartStatus, Date createDate, Date updateDate) {
		this.id = id;
		this.productCount = productCount;
		this.cartStatus = cartStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
