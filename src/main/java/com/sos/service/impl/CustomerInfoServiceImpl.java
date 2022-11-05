package com.sos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.CustomerInfoStatus;
import com.sos.entity.Account;
import com.sos.entity.CustomerInfo;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.CustomerInfoRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.CustomerInfoService;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

	@Autowired
	private CustomerInfoRepository customerInfoRepository;

	@Override
	public List<CustomerInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CustomerInfo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CustomerInfo> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerInfo save(CustomerInfo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CustomerInfo> findByAccountId(int accountId) {
		return customerInfoRepository.findByAccountId(accountId, CustomerInfoStatus.ACTIVE);
	}

	@Transactional
	@Override
	public CustomerInfo save(CustomerInfo customerInfo, AccountAuthentication authentication) {
		Date date = new Date();
		customerInfo.setId(0);
		customerInfo.setAccount(new Account(authentication.getId()));
		customerInfo.setCreateDate(date);
		customerInfo.setUpdateDate(date);
		customerInfo.setCustomerInfoStatus(CustomerInfoStatus.ACTIVE);
		customerInfoRepository.save(customerInfo);
		customerInfoRepository.setDefaultCustomerInfoIfNull(customerInfo, authentication.getId());
		return customerInfo;
	}

	@Transactional
	@Override
	public void setDefaultCustomerInfo(int id, AccountAuthentication authentication) {
		CustomerInfo customerInfo = customerInfoRepository
				.findCustomerInfo(id, authentication.getId(), CustomerInfoStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("CustomerInfo not found."));
		customerInfoRepository.setDefaultCustomerInfo(customerInfo, authentication.getId());
	}

	@Transactional
	@Override
	public void deactivateCustomerInfo(int id, AccountAuthentication authentication) {
		CustomerInfo customerInfo = customerInfoRepository
				.findCustomerInfo(id, authentication.getId(), CustomerInfoStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("CustomerInfo not found."));
		customerInfoRepository.setCustomerInfoStatus(customerInfo.getId(), CustomerInfoStatus.INACTIVE);
	}
}
