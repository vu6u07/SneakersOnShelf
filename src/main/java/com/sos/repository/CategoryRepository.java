package com.sos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant;
import com.sos.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Page<Category> findCategoriesByNameContaining(String name, Pageable pageable);
    @Query(value = "SELECT c FROM Category c WHERE (:query IS NULL OR c.name LIKE :query) AND (:activeStatus IS NULL OR c.activeStatus = :activeStatus)")
    Page<Category> findAll(String query, ApplicationConstant.ActiveStatus activeStatus, Pageable pageable);
}
