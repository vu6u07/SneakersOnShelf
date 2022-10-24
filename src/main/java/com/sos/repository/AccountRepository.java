package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.Account;
import com.sos.entity.Cart;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query(value = "SELECT new com.sos.entity.Account(a.id, a.username, a.password) FROM Account a WHERE a.username = :username AND a.accountStatus = :accountStatus")
	Optional<Account> findByUsername(String username, AccountStatus accountStatus);

	@Query(value = "SELECT new com.sos.entity.Account(a.id, a.username, a.email, a.fullname, a.googleOAuthEmail, a.facebookOAuthId, a.customerInfo, a.picture, a.point, a.createDate) FROM Account a WHERE a.id = :id")
	Optional<Account> findAccountDTOById(int id);

	@Modifying
	@Query(value = "UPDATE Account a SET a.cart = :cart WHERE a.id = :id")
	int updateCart(int id, Cart cart);
	
	@Query(value = "SELECT a.email FROM Account a WHERE a.id = :id")
	Optional<String> getAccountEmail(int id);

}
