package com.sos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.dto.CartDTO;
import com.sos.entity.CustomerInfo;

public interface CartService {

	public CartDTO getCartDTO();

	public CartDTO getCartDTO(int id);

	public CartDTO getCartDTO(int id, String userTokenQuery);

	public void addToCart(int id, int productId, String userTokenQuery);

	public void deleteCartItem(int id, int cartId, String userTokenQuery);

	public void submitCart(int id, String userTokenQuery, CustomerInfo customerInfo, PaymentMethod paymentMethod) throws JsonMappingException, JsonProcessingException;

}
