package com.sos.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sos.entity.Account;

public interface AccountService extends UserDetailsService, CrudService<Account, Integer> {

	Optional<Account> findAccountDTOById(int id);

}
