package com.sos.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.Account;
import com.sos.entity.Role;
import com.sos.repository.AccountRepository;
import com.sos.repository.RoleRepository;
import com.sos.security.CustomUserDetail;
import com.sos.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	// @formatter:off
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username, AccountStatus.ACTIVE)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found : " + username));
		Collection<GrantedAuthority> authorities = roleRepository.findByAccountId(account.getId())
				.stream()
				.map(Role::getName)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new CustomUserDetail(account.getId(), username, account.getPassword(), authorities);
	}
	// @formatter:on

	@Override
	public List<Account> findAll() {
		return null;
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Optional<Account> findById(Integer id) {
		return accountRepository.findById(id);
	}

	@Override
	public Account save(Account entity) {
		return null;
	}

	@Override
	public void deleteById(Integer id) {

	}

	public Optional<Account> findAccountDTOById(int id) {
		return accountRepository.findAccountDTOById(id);
	}

}
