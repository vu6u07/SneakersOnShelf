package com.sos.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.security.AccountAuthentication;

public interface PurchaseService {

	PurchaseInfoDTO findPurchaseDTO(UUID id, String userTokenQuery);

	PurchaseInfoDTO findPurchaseDTO(UUID id, AccountAuthentication authentication);

	Page<PurchaseDTO> findAllPurchaseDTOByAccountId(AccountAuthentication accountAuthentication, Pageable pageable);
	
}
