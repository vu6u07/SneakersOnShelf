package com.sos.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateDTO {

	private int id;

	private int score;

	private String comment;

	private Date createDate;

	private String size;

	private String fullname;

	private String picture;

}
