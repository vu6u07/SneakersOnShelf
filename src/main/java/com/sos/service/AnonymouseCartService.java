package com.sos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.dto.CartDTO;
import com.sos.entity.CustomerInfo;
import com.sos.entity.Order;

public interface AnonymouseCartService {

	public CartDTO createAnonymousCart();

	public CartDTO getCartDTO(int id, String token);

	public void addToCart(int id, int productId, int quantity, String token);
	
	public void changeCartItemQuantity(int id, int quantity, String token);

	public void deleteCartItem(int cartItemId, String token);
	
	public void deleteAllCartItem(int cartId, String token);

	public Order submitCart(int id, String token, CustomerInfo customerInfo, PaymentMethod paymentMethod, String email) throws JsonMappingException, JsonProcessingException;

}
