package com.sos.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private ProductDetail productDetail;

	private int quantity;

	private long price;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToOne
	private Rate rate;

	public OrderItem(int id) {
		this.id = id;
	}

	public OrderItem(int id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public OrderItem(int id, int productDetailId, int productDetailQuantity, String productDetailSize, String name, long sellPrice, int quantity) {
		this.id = id;
		this.productDetail = new ProductDetail(productDetailId, productDetailSize, productDetailQuantity);
		Product product = new Product();
		product.setSellPrice(sellPrice);
		product.setName(name);
		this.productDetail.setProduct(product);
		this.quantity = quantity;
	}

}
