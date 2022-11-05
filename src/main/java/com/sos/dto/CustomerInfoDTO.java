package com.sos.dto;

import java.util.Date;
import com.sos.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoDTO {
	
	private int id;

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

}
