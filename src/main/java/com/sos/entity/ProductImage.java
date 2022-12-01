package com.sos.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	public ProductImage(int id) {
		this.id = id;
	}

	public ProductImage(int id, String image) {
		this.id = id;
		this.image = image;
	}

}
