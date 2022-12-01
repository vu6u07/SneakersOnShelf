package com.sos.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sos.common.ApplicationConstant.OrderItemStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private ProductDetail productDetail;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToOne
	private Rate rate;
	
	@Enumerated(EnumType.STRING)
	private OrderItemStatus orderItemStatus;

	private int quantity;

	private long price;

	public OrderItem(int id) {
		this.id = id;
	}

	public OrderItem(int id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public OrderItem(int id, long price, int quantity, int productDetailId, int productDetailQuantity, String productDetailSize, String name, long sellPrice) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.productDetail = new ProductDetail(productDetailId, productDetailSize, productDetailQuantity);
		Product product = new Product();
		product.setSellPrice(sellPrice);
		product.setName(name);
		this.productDetail.setProduct(product);
	}
	
}
