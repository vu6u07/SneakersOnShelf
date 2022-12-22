package com.sos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sos.dto.StatisticDTO.BestSellingProductStatistic;
import com.sos.dto.StatisticDTO.OrderChartDataProjection;
import com.sos.dto.StatisticDTO.OrderStatisticProjection;
import com.sos.dto.StatisticDTO.OrderStatusStatisticProjection;
import com.sos.entity.Order;

public interface StatisticRepository extends JpaRepository<Order, String> {

	// @formatter:off
	@Query(value = 
			"SELECT * FROM \r\n" + 
			"(SELECT COALESCE(COUNT(o.id), 0) AS monthlyCount, COALESCE(SUM(o.total - o.discount), 0) AS monthlyAmount FROM orders o JOIN (SELECT DISTINCT order_id FROM order_timeline WHERE order_timeline_type = 'APPROVED' AND created_date >= :firstDayOfMonth AND DATE(created_date) <= :date) t ON t.order_id = o.id WHERE order_status = 'APPROVED' AND o.total > 0) monthly\r\n" + 
			"JOIN " + 
			"(SELECT COALESCE(COUNT(o.id), 0) AS dailyCount, COALESCE(SUM(o.total - o.discount), 0) AS dailyAmount FROM orders o JOIN (SELECT DISTINCT order_id FROM order_timeline WHERE order_timeline_type = 'APPROVED' AND DATE(created_date) = :date) t ON t.order_id = o.id WHERE order_status = 'APPROVED' AND o.total > 0) daily " + 
			"JOIN " + 
			"(SELECT COALESCE(SUM(oi.quantity), 0) AS 'monthlyProductQuantity' FROM orders o JOIN order_items oi ON o.id = oi.order_id WHERE  o.order_status = 'APPROVED' AND oi.order_item_status = 'APPROVED' AND DATE(o.create_date) >= :firstDayOfMonth AND DATE(o.create_date) <= :date) pro", nativeQuery = true)
	OrderStatisticProjection getOrderStatisticProjection(String date, String firstDayOfMonth);
	// @formatter:on

	@Query(value = 
			"SELECT \r\n" + 
			"t.date, COUNT(o.id) AS count, SUM(o.total - o.discount) AS amount \r\n" + 
			"FROM orders o\r\n" + 
			"JOIN ( SELECT\r\n" + 
			"DATE_FORMAT(MAX(created_date),'%d/%m/%Y') as date,\r\n" + 
			"order_id\r\n" + 
			"FROM order_timeline\r\n" + 
			"WHERE order_timeline_type = 'APPROVED'\r\n" + 
			"AND created_date >= :fromDate AND DATE(created_date) <= :toDate\r\n" + 
			"GROUP BY order_id ) t ON o.id = t.order_id\r\n" + 
			"WHERE o.order_status = 'APPROVED'\r\n" + 
			"GROUP BY t.date " +
			"ORDER BY t.date", nativeQuery = true)
	List<OrderChartDataProjection> getOrderChartDataProjection(String fromDate, String toDate);

	@Query(value = "SELECT new com.sos.dto.StatisticDTO$BestSellingProductStatistic(p.id, p.name, i.image, p.sellPrice, SUM(od.quantity) AS quantity) FROM OrderItem od JOIN od.order o JOIN od.productDetail pd JOIN pd.product p LEFT JOIN p.productImage i WHERE o.orderStatus = 'APPROVED' AND od.orderItemStatus = 'APPROVED' AND o.createDate >= :fromDate AND o.createDate < :toDate GROUP BY p.id, p.name, i.image, p.sellPrice, p.originalPrice ORDER BY quantity DESC")
	Page<BestSellingProductStatistic> findBestSellingProductDTO(Date fromDate, Date toDate, Pageable pageable);
	
	@Query(value = "SELECT order_status AS orderStatus, COUNT(id) AS count FROM orders WHERE create_date >= :fromDate AND create_date < :toDate GROUP BY order_status", nativeQuery = true)
	List<OrderStatusStatisticProjection> getOrderStatusStatisticProjections(String fromDate, String toDate);
	
}
