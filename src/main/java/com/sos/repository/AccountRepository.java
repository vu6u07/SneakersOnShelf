package com.sos.repository;

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
import com.sos.entity.Role;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query(value = "SELECT new com.sos.entity.Account(a.id, a.username, a.password) FROM Account a WHERE a.username = :username AND a.accountStatus = :accountStatus")
	Optional<Account> findByUsername(String username, AccountStatus accountStatus);

	@Query(value = "SELECT new com.sos.entity.Account(a.id, a.username, a.email, a.fullname, a.googleOAuthEmail, a.facebookOAuthId, c, a.picture, a.createDate, a.point) FROM Account a LEFT JOIN a.customerInfo c WHERE a.id = :id")
	Optional<Account> findAccountDTOById(int id);
	
	@Query(value = "SELECT new com.sos.entity.Account(a.id) FROM Account a WHERE a.username = :username AND a.email = :email")
	Optional<Account> findAccountIdByUsernameEmail(String username, String email);

	@Modifying
	@Query(value = "UPDATE Account a SET a.cart = :cart WHERE a.id = :id")
	int updateCart(int id, Cart cart);

	@Query(value = "SELECT a.email FROM Account a WHERE a.id = :id")
	Optional<String> getAccountEmail(int id);

	@Query(value = "SELECT new com.sos.entity.Account(a.id) FROM Account a WHERE a.googleOAuthEmail = :googleOAuthEmail AND a.accountStatus = :accountStatus")
	Optional<Account> findAccountByGoogleOAuthEmail(String googleOAuthEmail, AccountStatus accountStatus);

	@Query(value = "SELECT new com.sos.entity.Account(a.id) FROM Account a WHERE a.facebookOAuthId = :facebookOAuthId AND a.accountStatus = :accountStatus")
	Optional<Account> findAccountFacebookOAuthId(String facebookOAuthId, AccountStatus accountStatus);

	@Modifying
	@Query(value = "UPDATE Account a SET a.fullname = :fullname, a.email = :email WHERE a.id = :id")
	int updateAccountInfo(int id, String fullname, String email);

	@Modifying
	@Query(value = "UPDATE Account a SET a.password = :password WHERE a.id = :id AND a.username IS NOT NULL")
	int updatePassword(int id, String password);

	@Query(value = "SELECT a.password FROM Account a WHERE a.id = :id AND a.username IS NOT NULL")
	Optional<String> findAccountPassword(int id);

	// Admin
	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.username, a.email, a.fullname, a.picture, a.accountStatus, a.createDate, a.updateDate, a.point) FROM Account a WHERE a.id = :id")
	Optional<AccountDTO> findAccountInfoDTOById(int id);
	
	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.username, a.email, a.fullname, a.picture, a.accountStatus, a.createDate, a.updateDate, a.point) FROM Account a JOIN a.roles r WHERE a.id = :id AND :role IN r")
	Optional<AccountDTO> findAccountInfoDTOById(int id, Role role);

	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.username, a.email, a.fullname, a.picture, a.accountStatus, a.createDate, a.updateDate, a.point) FROM Account a WHERE (:accountStatus IS NULL OR a.accountStatus = :accountStatus) AND (:query IS NULL OR (CAST(a.id as string) LIKE :query OR a.email LIKE :query OR a.fullname LIKE :query))")
	Page<AccountDTO> findAccountDTOs(String query, AccountStatus accountStatus, Pageable pageable);
	
	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.username, a.email, a.fullname, a.picture, a.accountStatus, a.createDate, a.updateDate, a.point) FROM Account a JOIN a.roles r WHERE (:role IN r) AND (:accountStatus IS NULL OR a.accountStatus = :accountStatus) AND (:query IS NULL OR (CAST(a.id as string) LIKE :query OR a.email LIKE :query OR a.fullname LIKE :query))")
	Page<AccountDTO> findStaffAccountDTOs(String query, AccountStatus accountStatus, Role role, Pageable pageable);
	
	@Modifying
	@Query(value = "UPDATE Account a SET a.accountStatus = :accountStatus WHERE a.id = :id")
	int updateAccountStatus(int id, AccountStatus accountStatus);
	
}
