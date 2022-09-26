package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sos.common.ApplicationConstant.VerifyCodeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VerifyCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String value;

	@ManyToOne
	private Account account;

	private Date createDate;

	private Date updateDate;

	private Date experationDate;

	@Enumerated(EnumType.STRING)
	private VerifyCodeType verifyCodeType;

}
