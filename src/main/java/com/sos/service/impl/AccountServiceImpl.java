package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.Account;
import com.sos.repository.AccountRepository;
import com.sos.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountRepository accountRepo;

	@Override
	public List<Account> findAll() {
		return accountRepo.findAll();
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountRepo.findAll(pageable);
	}

	@Override
	public Optional<Account> findById(Integer id) {
		return accountRepo.findById(id);
	}

	@Override
	public Account save(Account entity) {
		return accountRepo.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		accountRepo.deleteAccountById(AccountStatus.INACTIVE, id);
		
	}

}
