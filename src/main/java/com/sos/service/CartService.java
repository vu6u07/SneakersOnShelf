package com.sos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.dto.CartDTO;
import com.sos.entity.Order;
import com.sos.security.AccountAuthentication;

public interface CartService {

	public CartDTO getOrCreateCart(AccountAuthentication authentication);

	public CartDTO getCartDTO(int id, AccountAuthentication authentication);

	public void addToCart(int id, int productId, int quantity, AccountAuthentication authentication);

	public void changeCartItemQuantity(int id, int quantity, AccountAuthentication authentication);

	public void deleteCartItem(int cartItemId, AccountAuthentication authentication);

	public void deleteAllCartItem(int cartId, AccountAuthentication authentication);

	public Order submitCart(int id, PaymentMethod paymentMethod, int customerInfoId,
			AccountAuthentication authentication) throws JsonMappingException, JsonProcessingException;

}
