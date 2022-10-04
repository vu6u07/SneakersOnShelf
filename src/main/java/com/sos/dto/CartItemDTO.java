package com.sos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

	private int id;

	private int quantity;

	private int productId;

	private String name;
	
	private String size;

	private String image;

	private long price;

}
