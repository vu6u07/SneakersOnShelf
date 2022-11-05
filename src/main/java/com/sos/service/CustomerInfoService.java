package com.sos.service;

import java.util.List;

import com.sos.entity.CustomerInfo;
import com.sos.security.AccountAuthentication;

public interface CustomerInfoService extends CrudService<CustomerInfo, Integer> {

	List<CustomerInfo> findByAccountId(int accountId);

	CustomerInfo save(CustomerInfo customerInfo, AccountAuthentication authentication);

	void setDefaultCustomerInfo(int id, AccountAuthentication authentication);
	
	void deactivateCustomerInfo(int id, AccountAuthentication authentication);

}
