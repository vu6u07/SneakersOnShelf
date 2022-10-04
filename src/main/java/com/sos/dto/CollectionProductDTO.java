package com.sos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionProductDTO {

	private int id;

	private String name;

	private String image;

	private long sellPrice;

	private long originalPrice;

}
