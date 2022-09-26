package com.sos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int score;

	private String comment;

	private Date createDate;

}
