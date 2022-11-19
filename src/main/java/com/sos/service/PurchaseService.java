package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.security.AccountAuthentication;

public interface PurchaseService {

	PurchaseInfoDTO findPurchaseDTO(String id, String userTokenQuery);

	PurchaseInfoDTO findPurchaseDTO(String id, AccountAuthentication authentication);

	Page<PurchaseDTO> findAllPurchaseDTOByAccountId(AccountAuthentication accountAuthentication, Pageable pageable);
	
}
