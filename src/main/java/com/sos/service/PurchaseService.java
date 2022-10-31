package com.sos.service;

import java.util.UUID;

import com.sos.dto.PurchaseInfoDTO;
import com.sos.security.AccountAuthentication;

public interface PurchaseService {

	PurchaseInfoDTO findPurchaseDTO(UUID id, String userTokenQuery);

	PurchaseInfoDTO findPurchaseDTO(UUID id, AccountAuthentication authentication);

}
