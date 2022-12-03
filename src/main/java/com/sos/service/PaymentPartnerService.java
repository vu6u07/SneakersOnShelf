package com.sos.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface PaymentPartnerService<T> {

	String getPayUrl(T entity) throws Exception;
	
	void confirmTransaction(int id, JsonNode data);
	
}
