package com.sos.repository;

import com.sos.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT b FROM Category b WHERE b.id = :id")
    Category findCategoryById(@Param("id") Integer id);


    @Query(value = "SELECT b FROM Category b WHERE b.name = :name")
    Category findCategoryByName(@Param("name") String name);
}
