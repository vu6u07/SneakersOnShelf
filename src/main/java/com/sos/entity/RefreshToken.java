package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	private String value;

	private Date createDate;

	private Date experationDate;

	public RefreshToken(Account account, String value, Date createDate, Date experationDate) {
		this.account = account;
		this.value = value;
		this.createDate = createDate;
		this.experationDate = experationDate;
	}

}
