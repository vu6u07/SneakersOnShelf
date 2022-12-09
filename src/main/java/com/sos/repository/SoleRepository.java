package com.sos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Sole;

@Repository
public interface SoleRepository extends JpaRepository<Sole, Integer> {

	@Query(value = "SELECT m FROM Sole m WHERE (:query IS NULL OR m.name LIKE :query) AND (:activeStatus IS NULL OR m.activeStatus = :activeStatus)")
	Page<Sole> findAll(String query, ActiveStatus activeStatus, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Sole m SET m.activeStatus = :activeStatus WHERE m.id = :id")
	int updateSoleStatus(int id, ActiveStatus activeStatus);

	@Modifying
	@Query(value = "UPDATE Sole m SET m.name = :name WHERE m.id = :id")
	int updateSoleName(int id, String name);

}
