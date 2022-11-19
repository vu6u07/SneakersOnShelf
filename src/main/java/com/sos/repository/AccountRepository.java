package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.dto.AccountDTO;
import com.sos.entity.Account;
import com.sos.entity.Cart;

public interface AccountRepository extends JpaRepository<Account, Integer> {

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



	// Admin
	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.username, a.email, a.fullname, a.picture, a.createDate, a.updateDate) FROM Account a WHERE a.id = :id AND a.accountStatus = :accountStatus")
	Optional<AccountDTO> findAccountDTOById(int id, AccountStatus accountStatus);

	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.username, a.email, a.fullname, a.picture, a.createDate, a.updateDate) FROM Account a WHERE a.accountStatus = :accountStatus")
	Page<AccountDTO> findAccountDTOs(AccountStatus accountStatus, Pageable pageable);

	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.username, a.email, a.fullname, a.picture, a.createDate, a.updateDate) FROM Account a WHERE a.accountStatus = :accountStatus AND (CAST(a.id as string) LIKE :query OR a.email LIKE :query OR a.fullname LIKE :query)")
	Page<AccountDTO> findAccountDTOs(String query, AccountStatus accountStatus, Pageable pageable);
}
