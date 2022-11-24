package com.sos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sos.dto.AccountDTO;
import com.sos.entity.Account;

public interface AccountService extends UserDetailsService, CrudService<Account, Integer> {

	Optional<Account> findAccountDTOById(int id);

	AccountDTO findAccountReportDTOById(int id);

	Page<AccountDTO> findAccoutDTOs(Pageable pageable);

	Page<AccountDTO> findAccoutDTOs(String query, Pageable pageable);

	void updateAccountInfo(int id, String fullname, String email);
	
}
