package com.sos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sos.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
