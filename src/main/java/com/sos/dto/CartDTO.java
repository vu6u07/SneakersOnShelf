package com.sos.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

	private int id;

	private String token;
	
	private List<CartItemDTO> items;

	public CartDTO(int id) {
		this.id = id;
	}

	public CartDTO(int id, String token) {
		this.id = id;
		this.token = token;
	}

	public CartDTO(int id, List<CartItemDTO> items) {
		this.id = id;
		this.items = items;
	}

}
