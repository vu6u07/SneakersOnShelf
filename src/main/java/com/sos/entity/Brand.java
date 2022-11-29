package com.sos.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sos.common.ApplicationConstant.ActiveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@Enumerated(EnumType.STRING)
	private ActiveStatus activeStatus;
	
}
