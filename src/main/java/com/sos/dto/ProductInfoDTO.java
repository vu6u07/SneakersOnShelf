package com.sos.dto;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.entity.ProductDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO {

	private int id;

	private String name;

	@Enumerated(EnumType.STRING)
	private ProductGender productGender;

	private String brand;

	private String category;

	private String productImage;

	private List<String> productImages;

	private List<ProductDetail> productDetails;
	
	private long sellPrice;

	private long originalPrice;

	private String description;
	
	private float score;

	public ProductInfoDTO(int id, String name, ProductGender productGender, String brand, String category,
			String productImage, long sellPrice, long originalPrice, String description) {
		this.id = id;
		this.name = name;
		this.productGender = productGender;
		this.brand = brand;
		this.category = category;
		this.productImage = productImage;
		this.sellPrice = sellPrice;
		this.originalPrice = originalPrice;
		this.description = description;
	}

}
