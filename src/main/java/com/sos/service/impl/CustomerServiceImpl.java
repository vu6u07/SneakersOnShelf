package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sos.entity.CustomerInfo;
import com.sos.repository.CustomerInfoRepository;
import com.sos.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerInfoRepository customerRepo;
	
	@Override
	public List<CustomerInfo> findAll() {
		return customerRepo.findAll();
	}

	@Override
	public Page<CustomerInfo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return customerRepo.findAll(pageable);
	}

	@Override
	public Optional<CustomerInfo> findById(Integer id) {
		// TODO Auto-generated method stub
		return customerRepo.findById(id);
	}

	@Override
	public CustomerInfo save(CustomerInfo entity) {
		// TODO Auto-generated method stub
		return customerRepo.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
	}

	@Override
	public Optional<CustomerInfo> getByAccountId(int accountId) {
		return customerRepo.findCustomerByAccountId(accountId);
	}

}
