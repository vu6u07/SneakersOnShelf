package com.sos.repository;

import com.sos.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sos.entity.Brand;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(value = "SELECT b FROM Brand b WHERE b.id = :id")
    Brand findBrandById(@Param("id") Integer id);

    @Query(value = "SELECT b FROM Brand b WHERE b.name = :name")
    Brand findBrandByName(@Param("name") String name);
}
