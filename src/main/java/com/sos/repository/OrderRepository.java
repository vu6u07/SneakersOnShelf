package com.sos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
