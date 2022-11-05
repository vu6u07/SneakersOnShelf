package com.sos.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonIgnoreProperties("cartItems")
	@ManyToOne
	private Cart cart;

	@JsonIgnoreProperties("product")
	@ManyToOne
	private ProductDetail productDetail;

	private int quantity;
	
	public CartItem(int id) {
		this.id = id;
	}

	public CartItem(Cart cart, ProductDetail productDetail, int quantity) {
		this.cart = cart;
		this.productDetail = productDetail;
		this.quantity = quantity;
	}

	public CartItem(int id, ProductDetail productDetail, int quantity) {
		this.id = id;
		this.productDetail = productDetail;
		this.quantity = quantity;
	}

}
