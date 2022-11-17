package com.sos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.dto.OrderTimelineDTO;
import com.sos.entity.OrderTimeline;

public interface OrderTimelineRepository extends JpaRepository<OrderTimeline, Integer> {

	@Query(value = "SELECT new com.sos.dto.OrderTimelineDTO(o.id, a.fullname, o.createdDate, o.orderTimelineType, o.description) FROM OrderTimeline o LEFT JOIN o.staff a WHERE o.order.id = :orderId")
	List<OrderTimelineDTO> findOrderTimelineDTOsByOrderId(String orderId);
	
}
