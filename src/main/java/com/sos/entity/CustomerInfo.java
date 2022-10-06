package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	private String fullname;

	private String phone;

	private int provinceId;

	private String provinceName;

	private int districtId;

	private String districtName;

	private String wardCode;

	private String wardName;

	private String address;

	private Date createDate;

	private Date updateDate;

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
