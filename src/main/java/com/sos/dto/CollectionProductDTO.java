package com.sos.dto;

import java.util.Date;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;

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
	
	private ProductGender productGender;
	
	private ProductStatus productStatus;
	
	private Date createDate;
	
	private Date updateDate;

	public CollectionProductDTO(int id, String name, String image, long sellPrice, long originalPrice) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.sellPrice = sellPrice;
		this.originalPrice = originalPrice;
	}

}
