package com.sos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

	@Query(value = "SELECT b FROM Brand b WHERE b.activeStatus = :activeStatus")
	List<Brand> findAll(ActiveStatus activeStatus);
	
	@Query(value = "SELECT b FROM Brand b WHERE (:query IS NULL OR b.name LIKE :query) AND (:activeStatus IS NULL OR b.activeStatus = :activeStatus)")
	Page<Brand> findAll(String query, ActiveStatus activeStatus, Pageable pageable);
	
}
