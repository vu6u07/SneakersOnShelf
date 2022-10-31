package com.sos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

	@Query(value = "SELECT o.delivery FROM Order o WHERE o.id = :orderId")
	Delivery findByOrderId(UUID orderId);

}
