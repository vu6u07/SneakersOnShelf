package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query(value = "SELECT new com.sos.entity.Account(a.id, a.email, a.password) FROM Account a WHERE a.email = :email AND a.accountStatus = :accountStatus")
	Optional<Account> findByEmail(String email, AccountStatus accountStatus);

}
