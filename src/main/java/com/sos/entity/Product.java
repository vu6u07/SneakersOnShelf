package com.sos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@Enumerated(EnumType.STRING)
	private ProductGender productGender;

	@Enumerated(EnumType.STRING)
	private ProductStatus productStatus;

	@ManyToOne
	private Brand brand;

	@ManyToOne
	private Category category;

	@JsonIgnoreProperties("product")
	@OneToOne
	private ProductImage productImage;

	@JsonIgnoreProperties("product")
	@OneToMany(mappedBy = "product")
	private List<ProductDetail> productDetails;

	private long sellPrice;

	private long originalPrice;

	private String description;

	private Date createDate;

	private Date updateDate;

	public Product(int id) {
		this.id = id;
	}

}
