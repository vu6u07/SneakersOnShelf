package com.sos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {

	@Query(value = "SELECT c FROM Color c WHERE (:query IS NULL OR c.code LIKE :query) AND (:activeStatus IS NULL OR c.activeStatus = :activeStatus)")
	Page<Color> findAll(String query, ActiveStatus activeStatus, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Color c SET c.code = :code WHERE c.id = :id")
	int updateColor(int id, String code);

	@Modifying
	@Query(value = "UPDATE Color c SET c.activeStatus = :activeStatus WHERE c.id = :id")
	int updateColorStatus(int id, ActiveStatus activeStatus);

}
