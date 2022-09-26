package com.sos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sos.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
