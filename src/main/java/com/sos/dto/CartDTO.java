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

	private String userTokenQuery;

	private List<CartItemDTO> items;

	public CartDTO(int id, String userTokenQuery) {
		this.id = id;
		this.userTokenQuery = userTokenQuery;
	}

}
