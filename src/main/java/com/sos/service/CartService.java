package com.sos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.SaleMethod;
import com.sos.dto.CartDTO;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;
import com.sos.entity.Voucher;

public interface CartService<T> {

	CartDTO createCart(T authentication);

	CartDTO getCartDTOById(int id, T authentication);

	void addToCart(int id, int productId, int quantity, T authentication);

	void changeCartItemQuantity(int id, int quantity, T authentication);

	void deleteCartItem(int cartItemId, T authentication);

	void deleteAllCartItem(int cartId, T authentication);

	Order submitCart(int id, CustomerInfo customerInfo, String email, SaleMethod saleMethod, Voucher voucher, T authentication)
			throws JsonMappingException, JsonProcessingException;

}
