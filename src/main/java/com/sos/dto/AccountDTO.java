package com.sos.dto;
import java.util.Date;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	
	private int id;

	private String email;

	private String password;

	private Set<Role> roles;

	private String googleOAuthEmail;

	private String facebookOAuthId;

	private CustomerInfo customerInfo;

	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	private String picture;

	private long point;

	private Date createDate;

	private Date updateDate;
	
	

}
