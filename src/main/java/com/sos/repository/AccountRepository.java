package com.sos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE Account a SET a.accountStatus = :status WHERE a.id = :id")
	void deleteAccountById(AccountStatus status, int id);
	
	
}
