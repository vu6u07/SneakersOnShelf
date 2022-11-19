package com.sos.dto;

import java.util.Date;
import java.util.List;

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

	private Date createDate;

	private Date updateDate;

	private List<CustomerInfo> customerInfos;

	public AccountDTO(int id, String username, String email, String fullname, String picture, Date createDate,
			Date updateDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.fullname = fullname;
		this.picture = picture;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
