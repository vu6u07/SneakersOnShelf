package com.sos.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

	private String email;

	private String password;

	@ManyToMany
	private Set<Role> roles;

	@Column(name = "google_oauth_email")
	private String googleOAuthEmail;

	@Column(name = "facebook_oauth_id")
	private String facebookOAuthId;

	@OneToOne
	private CustomerInfo customerInfo;

	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	private String picture;

	private long point;

	private Date createDate;

	private Date updateDate;

	public Account(int id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public Account(int id) {
		this.id = id;
	}

}
