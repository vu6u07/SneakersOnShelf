package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	private String code;

	private long amount;

	private long requiredValue;

	private long redeemValue;

	@Enumerated(EnumType.STRING)
	private VoucherType voucherType;

	private Date createDate;

	private Date updateDate;

	private Date experationDate;

}
