package com.sos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderMomoRequest {
	
	private String partnerCode;
	
	private String requestId;
	
	private long amount;
	
	private String orderId;
	
	private String orderInfo;
	
	private String redirectUrl;
	
	private String ipnUrl;
	
	private String requestType;
	
	private String extraData;
	
	private String lang;
	
	private String signature;
	
}
