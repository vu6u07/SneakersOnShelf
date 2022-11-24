package com.sos.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.sos.dto.StatisticDTO.BestSellingProductStatistic;
import com.sos.dto.StatisticDTO.OrderChartDataProjection;
import com.sos.dto.StatisticDTO.OrderStatisticProjection;
import com.sos.dto.StatisticDTO.OrderStatusStatisticProjection;

public interface StatisticService {

	OrderStatisticProjection getOrderStatisticProjection(Date date);
	
	List<OrderChartDataProjection> getOrderChartDataProjection(Date fromDate, Date toDate);
	
	Page<BestSellingProductStatistic> getBestSellingProductStatistic(Date fromDate, Date toDate);

	List<OrderStatusStatisticProjection> getOrderStatusStatisticProjections(Date fromDate, Date toDate);
	
}
