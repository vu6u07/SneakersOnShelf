package com.sos.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sos.common.ApplicationConstant.VoucherAccess;
import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.common.ApplicationConstant.VoucherType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Voucher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String code;
	
	@ManyToOne
	private Account staff;

	private long amount;

	private long requiredValue;

	private long maxValue;

	private int quantity;

	@Enumerated(EnumType.STRING)
	private VoucherStatus voucherStatus;

	@Enumerated(EnumType.STRING)
	private VoucherType voucherType;

	@Enumerated(EnumType.STRING)
	private VoucherAccess voucherAccess;

	private Date createDate;

	private Date startDate;

	private Date experationDate;

	public Voucher(int id) {
		this.id = id;
	}

	public Voucher(long amount) {
		this.amount = amount;
	}

	public Voucher(int id, String code, long amount, long requiredValue, long maxValue, int quantity,
			VoucherStatus voucherStatus, VoucherType voucherType, VoucherAccess voucherAccess, Date createDate,
			Date startDate, Date experationDate) {
		this.id = id;
		this.code = code;
		this.amount = amount;
		this.requiredValue = requiredValue;
		this.maxValue = maxValue;
		this.quantity = quantity;
		this.voucherStatus = voucherStatus;
		this.voucherType = voucherType;
		this.voucherAccess = voucherAccess;
		this.createDate = createDate;
		this.startDate = startDate;
		this.experationDate = experationDate;
	}
	
}
