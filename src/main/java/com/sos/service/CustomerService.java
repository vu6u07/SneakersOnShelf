package com.sos.service;

import java.util.Optional;

import com.sos.entity.CustomerInfo;

public interface CustomerService extends CrudService<CustomerInfo, Integer> {
	
	public Optional<CustomerInfo> getByAccountId(int accountId);
	
}
