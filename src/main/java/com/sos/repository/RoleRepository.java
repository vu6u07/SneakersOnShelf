package com.sos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query(value = "SELECT a.roles FROM Account a WHERE a.id = :id")
	List<Role> findByAccountId(int id);
	
	Optional<Role> findByName(String name);
	
}
