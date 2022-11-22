package com.sos.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.common.ApplicationConstant.CustomerInfoStatus;
import com.sos.dto.AccountDTO;
import com.sos.entity.Account;
import com.sos.entity.Role;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.AccountRepository;
import com.sos.repository.CustomerInfoRepository;
import com.sos.repository.RoleRepository;
import com.sos.security.CustomUserDetail;
import com.sos.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerInfoRepository customerInfoRepository;

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

	@Override
	public AccountDTO findAccountReportDTOById(int id) {
		AccountDTO rs = accountRepository.findAccountDTOById(id, AccountStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản."));
		rs.setCustomerInfos(customerInfoRepository.findByAccountId(rs.getId(), CustomerInfoStatus.ACTIVE));
		return rs;
	}

	@Override
	public Page<AccountDTO> findAccoutDTOs(Pageable pageable) {
		return accountRepository.findAccountDTOs(AccountStatus.ACTIVE, pageable);
	}

	@Override
	public Page<AccountDTO> findAccoutDTOs(String query, Pageable pageable) {
		return accountRepository.findAccountDTOs("%".concat(query).concat("%"), AccountStatus.ACTIVE, pageable);
	}

	@Transactional
	@Override
	public void updateAccountInfo(int id, String fullname, String email) {
		accountRepository.updateAccountInfo(id, fullname, email);
	}

}
