package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.dto.AccountDTO;
import com.sos.entity.MemberOfferPolicy;

@Repository
public interface MemberOfferPolicyRepository extends JpaRepository<MemberOfferPolicy, Integer> {

	@Query(value = "SELECT a.point FROM Account a WHERE a.id = :id")
	Optional<Long> getPointByAccountId(int id);
	
	@Query(value = "SELECT new com.sos.dto.AccountDTO(a.id, a.point) FROM Order o JOIN o.account a WHERE o.id = :id")
	Optional<AccountDTO> getAccountDTOByOrderId(String id);
	
	@Modifying
	@Query(value = "UPDATE Account a SET a.point = :point WHERE a.id = :id")
	int updateAccountPoint(int id, long point);
	
	@Modifying
	@Query(value = "UPDATE MemberOfferPolicy m SET m.offer = :offer, m.requiredPoint = :requiredPoint WHERE m.id = :id")
	int updateMemberOfferPolicy(int id, int offer, long requiredPoint);
	
}
