package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.CartStatus;
import com.sos.dto.CartReportDTO;
import com.sos.security.AccountAuthentication;

public interface AdminCartService extends CartService<AccountAuthentication> {

	Page<CartReportDTO> findCartReportDTO(AccountAuthentication authentication, CartStatus cartStatus,
			Pageable pageable);

	Page<CartReportDTO> findCartReportDTO(AccountAuthentication authentication, int id, Pageable pageable);

	void deleteCart(int id, AccountAuthentication authentication);
	
}
