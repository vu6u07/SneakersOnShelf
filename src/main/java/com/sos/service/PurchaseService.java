package com.sos.service;

import com.sos.dto.PurchaseInfoDTO;

public interface PurchaseService {

	PurchaseInfoDTO findPurchaseDTO(int id, String userTokenQuery);

}
