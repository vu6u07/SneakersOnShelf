package com.sos.service;

import com.sos.dto.CartDTO;

public interface CartService {

	public CartDTO getCartDTO();

	public CartDTO getCartDTO(int id);

	public CartDTO getCartDTO(int id, String userTokenQuery);

	public void addToCart(int id, int productId, String userTokenQuery);

}
