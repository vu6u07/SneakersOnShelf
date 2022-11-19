package com.sos.dto;

import java.util.Date;

import com.sos.common.ApplicationConstant.OrderTimelineType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderTimelineDTO {

	private int id;

	private String staff;

	private Date createdDate;

	private OrderTimelineType orderTimelineType;

	private String description;

}
