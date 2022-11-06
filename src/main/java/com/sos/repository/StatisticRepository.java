package com.sos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.dto.OrderChartDataProjection;
import com.sos.dto.OrderStatisticProjection;
import com.sos.entity.Order;

public interface StatisticRepository extends JpaRepository<Order, UUID> {

	// @formatter:off
	@Query(value = 
			"SELECT * FROM \r\n" + 
			"(SELECT COUNT(o.id) AS monthlyOrderCount, COALESCE(SUM(oi.quantity), 0) AS monthlyProductCount, COALESCE(SUM(o.total), 0) AS monthlyOrderAmount FROM orders o JOIN order_items oi ON o.id = oi.order_id WHERE o.order_status = 'APPROVED' AND o.update_date >= :firstDayOfMonth AND DATE(o.update_date) <= :date) monthly\r\n" + 
			"JOIN \r\n" + 
			"(SELECT COUNT(o.id) AS dailyOrderCount, COALESCE(SUM(oi.quantity), 0) AS dailyProductCount, COALESCE(SUM(o.total), 0) AS dailyOrderAmount FROM orders o JOIN order_items oi ON o.id = oi.order_id WHERE DATE(o.update_date) = :date) daily\r\n" + 
			"JOIN \r\n" + 
			"(SELECT COUNT(o.id) AS monthlyCancelledOrderCount, COALESCE(SUM(oi.quantity), 0) AS monthlyCancelledProductCount, COALESCE(SUM(o.total), 0) AS monthlyCancelledOrderAmount FROM orders o JOIN order_items oi ON o.id = oi.order_id WHERE o.order_status = 'CANCELLED' AND o.update_date >= :firstDayOfMonth AND DATE(o.update_date) <= :date) monthlyCancelled \r\n" + 
			"JOIN \r\n" + 
			"(SELECT COUNT(o.id) AS monthlyTotalOrderCount FROM orders o WHERE o.update_date >= :firstDayOfMonth AND DATE(o.update_date) <= :date) monthlyTotal", nativeQuery = true)
	OrderStatisticProjection getOrderStatisticProjection(String date, String firstDayOfMonth);
	// @formatter:on

	@Query(value = "SELECT \r\n"
			+ "DATE(o.update_date) AS date, COUNT(o.id) AS count, SUM(o.total) AS amount, SUM(oi.quantity) AS productCount\r\n"
			+ "FROM orders o JOIN order_items oi ON o.id = oi.order_id\r\n"
			+ "WHERE o.order_status = 'APPROVED' AND o.update_date >= :fromDate AND DATE(o.update_date) <= :toDate \r\n"
			+ "GROUP BY date", nativeQuery = true)
	List<OrderChartDataProjection> getOrderChartDataProjection(String fromDate, String toDate);

}
