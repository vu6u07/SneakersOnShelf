package com.sos.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	Optional<RefreshToken> findByValue(String value);
	
	@Query(value = "SELECT r.account.id FROM RefreshToken r WHERE r.value = :token AND r.experationDate >= :date")
	Optional<Integer> findAccountIdByTokenValue(String token, Date date);
	
}
