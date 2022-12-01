package com.sos.dto;

import java.util.Date;
import java.util.List;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.CustomerInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO {

	private int id;

	private String username;

	private String email;

	private String fullname;

	private String picture;
	
	private AccountStatus accountStatus;

	private Date createDate;

	private Date updateDate;

	private List<CustomerInfo> customerInfos;
	
	private boolean admin;
	
	public AccountDTO(int id, String username, String email, String fullname, String picture, Date createDate, Date updateDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.fullname = fullname;
		this.picture = picture;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public AccountDTO(int id, String username, String email, String fullname, String picture, AccountStatus accountStatus, Date createDate, Date updateDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.fullname = fullname;
		this.picture = picture;
		this.accountStatus = accountStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
}
