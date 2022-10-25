package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.sos.common.ApplicationConstant.CustomerInfoStatus;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_info")
public class CustomerInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Account account;

	@NotBlank(message = "Vui lòng nhập họ và tên.")
	private String fullname;

	@NotBlank(message = "Vui lòng nhập số điện thoại.")
	private String phone;

	@NotNull
	private int provinceId;

	@NotBlank
	private String provinceName;

	@NotNull
	private int districtId;

	@NotBlank
	private String districtName;

	@NotBlank
	private String wardCode;

	@NotBlank
	private String wardName;

	@NotBlank(message = "Vui lòng nhập địa chỉ.")
	private String address;
	
	@Enumerated(EnumType.STRING)
	private CustomerInfoStatus customerInfoStatus;

	private Date createDate;

	private Date updateDate;
	
	public CustomerInfo(int id) {
		this.id = id;
	}

	public CustomerInfo(int id, String fullname, String phone, int provinceId, String provinceName, int districtId,
			String districtName, String wardCode, String wardName, String address, Date createDate, Date updateDate) {
		this.id = id;
		this.fullname = fullname;
		this.phone = phone;
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.districtId = districtId;
		this.districtName = districtName;
		this.wardCode = wardCode;
		this.wardName = wardName;
		this.address = address;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
