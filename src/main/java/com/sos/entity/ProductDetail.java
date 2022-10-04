package com.sos.entity;

import javax.persistence.Entity;
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
public class ProductDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String size;

	private int quantity;

	@ManyToOne
	private Product product;

	public ProductDetail(int id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public ProductDetail(int id, String size, int quantity) {
		this.id = id;
		this.size = size;
		this.quantity = quantity;
	}

}
