package com.sos.dto;

import com.sos.entity.Rate;

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
	
	private int productDetailId;

	private String name;
	
	private String size;

	private String image;

	private long price;

	private Rate rate;

	public CartItemDTO(int id, int quantity, int productId, int productDetailId, String name, String size, String image,
			long price) {
		this.id = id;
		this.quantity = quantity;
		this.productId = productId;
		this.productDetailId = productDetailId;
		this.name = name;
		this.size = size;
		this.image = image;
		this.price = price;
	}
	
	
	
}
