package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.entity.Account;
import com.sos.entity.Cart;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE Account a SET a.accountStatus = :status WHERE a.id = :id")
	void deleteAccountById(AccountStatus status, int id);

	@Query(value = "SELECT new com.sos.entity.Account(a.id, a.username, a.password) FROM Account a WHERE a.username = :username AND a.accountStatus = :accountStatus")
	Optional<Account> findByUsername(String username, AccountStatus accountStatus);

	@Query(value = "SELECT new com.sos.entity.Account(a.id, a.username, a.email, a.fullname, a.googleOAuthEmail, a.facebookOAuthId, c, a.picture, a.point, a.createDate) FROM Account a LEFT JOIN a.customerInfo c WHERE a.id = :id")
	Optional<Account> findAccountDTOById(int id);

	@Modifying
	@Query(value = "UPDATE Account a SET a.cart = :cart WHERE a.id = :id")
	int updateCart(int id, Cart cart);


	@Query(value = "SELECT a.email FROM Account a WHERE a.id = :id")
	Optional<String> getAccountEmail(int id);

	@Query(value = "SELECT new com.sos.entity.Account(a.id) FROM Account a WHERE a.googleOAuthEmail = :googleOAuthEmail AND a.accountStatus = :accountStatus")
	Optional<Account> findAccountByGoogleOAuthEmail(String googleOAuthEmail, AccountStatus accountStatus);


	@Query(value = "SELECT new com.sos.entity.Account(a.id) FROM Account a WHERE a.facebookOAuthId = :facebookOAuthId AND a.accountStatus = :accountStatus")
	Optional<Account> findAccountFacebookOAuthId(String facebookOAuthId, AccountStatus accountStatus);


}
