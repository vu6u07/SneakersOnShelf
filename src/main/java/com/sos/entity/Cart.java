package com.sos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sos.common.ApplicationConstant.CartStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;
	
	private String token;

	@Enumerated(EnumType.STRING)
	private CartStatus cartStatus;

	@JsonIgnoreProperties("cart")
	@OneToMany(mappedBy = "cart")
	private List<CartItem> cartItems;

	private Date createDate;

	private Date updateDate;

	public Cart(int id) {
		this.id = id;
	}

	public Cart(int id, String token) {
		this.id = id;
		this.token = token;
	}

	public Cart(int id, String token, CartStatus cartStatus, Date createDate, Date updateDate) {
		this.id = id;
		this.token = token;
		this.cartStatus = cartStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

}
