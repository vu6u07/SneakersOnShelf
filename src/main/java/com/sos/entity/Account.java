package com.sos.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sos.common.ApplicationConstant.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	private String email;

	private String fullname;

	@ManyToMany
	private Set<Role> roles;

	@Column(name = "google_oauth_email")
	private String googleOAuthEmail;

	@Column(name = "facebook_oauth_id")
	private String facebookOAuthId;

	@JsonIgnoreProperties("account")
	@OneToOne
	private CustomerInfo customerInfo;

	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	@OneToOne(fetch = FetchType.LAZY)
	private Cart cart;

	private String picture;

	private Date createDate;

	private Date updateDate;

	public Account(int id) {
		this.id = id;
	}

	public Account(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Account(int id, String username, String email, String fullname, String googleOAuthEmail,
			String facebookOAuthId, CustomerInfo customerInfo, String picture, Date createDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.fullname = fullname;
		this.googleOAuthEmail = googleOAuthEmail;
		this.facebookOAuthId = facebookOAuthId;
		this.customerInfo = customerInfo;
		this.picture = picture;
		this.createDate = createDate;
	}

}
