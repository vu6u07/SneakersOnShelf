package com.sos.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.dto.AccountDTO;
import com.sos.entity.Account;

public interface AccountService extends UserDetailsService, CrudService<Account, Integer> {

	Optional<Account> findAccountDTOById(int id);

	AccountDTO findAccountReportDTOById(int id);
	
	AccountDTO findStaffAccountReportDTOById(int id);
	
	Page<AccountDTO> findAccoutDTOs(String query, AccountStatus accountStatus, Pageable pageable);
	
	Page<AccountDTO> findStaffAccoutDTOs(String query, AccountStatus accountStatus, Pageable pageable);

	void updateAccountInfo(int id, String fullname, String email);
	
	void updateAccountStatus(int id, AccountStatus accountStatus);
	
}
